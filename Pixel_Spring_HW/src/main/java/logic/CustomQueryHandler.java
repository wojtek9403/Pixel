package logic;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomQueryHandler {

	private final EntityManager entityManager;

	private static final Logger log = LoggerFactory.getLogger(CustomQueryHandler.class);

	public CustomQueryHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	// -------------------------------------------methods to fill join tables etc------------------------------//

	@Transactional
	public <T> void insertDataIntoDBbyFields(List<T> listOfEntities, Class<T> clazzOfEntity, String insertQuery) {

		insertQuery = insertQuery.toUpperCase();
		boolean queryParametersEqualsFields = true;
		long countOfParamsInQuery = insertQuery.chars().filter(c -> c == '?').count();
		long countOfDeclaredFieldsInEntity = clazzOfEntity.getDeclaredFields().length;
		String[] fieldsFromQuery = null;

		if (countOfParamsInQuery == countOfDeclaredFieldsInEntity) {

			Matcher matcher = Pattern.compile("(?:INSERT).+?\\((.+?)\\).+").matcher(insertQuery);

			if (matcher.find()) {
				MatchResult result = matcher.toMatchResult();
				fieldsFromQuery = result.group(1).toString().replaceAll("_", "").split("\\s*,\\s*");
			}

			for (int i = 0; i < countOfDeclaredFieldsInEntity; i++) {

				if (!fieldsFromQuery[i]
						.equals(clazzOfEntity.getDeclaredFields()[i].getName().toUpperCase().replaceAll("_", ""))) {
					queryParametersEqualsFields = false;
				}

			}

			if (queryParametersEqualsFields) {

				Query executeableQuery = entityManager.createNativeQuery(insertQuery);

				for (T e : listOfEntities) {

					try {

						for (int i = 0; i < countOfDeclaredFieldsInEntity; i++) {

							Field field = clazzOfEntity.getDeclaredFields()[i];
							field.setAccessible(true);
							Object value = (Object) field.get(e);

							executeableQuery.setParameter(i + 1, value);
						}

						executeableQuery.executeUpdate();

					} catch (IllegalArgumentException | SecurityException | IllegalAccessException e1) {
						log.info("Exception in insertDataIntoDB method: " + e1.getMessage() + " "
								+ e1.getLocalizedMessage());
						e1.printStackTrace();
						break;
					}
				}
			}

		} else {

			log.info("number and order of parameters in query has to be same as fields in entity : "
					+ "fillInTableUsingReflection(List<T> listOfEntities, Class<T> clazzOfEntity, String insertQuery)"
					+ " Not all data passed to database !");
		}

	}

}
