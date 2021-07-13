<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Practitioners</title>
</head>
<body>
	
		<label>Practitioners</label>
			
	<div align="center">
		
		<form method="get" action="Practitioners">
			<label>Specialization: &nbsp </label> 
			<input name="specialization" type="text" placeholder="Pediatrics" />
			
			<input type="submit" value="Szukaj" />
		</form>
		</br>
		<label>Specialization &nbsp : &nbsp Number of Visits</label> 

		<hr size="1" width="90%" color="gray">

		<div>
		
			<c:forEach var="practitioner" items="${practitioners}">

				<div id="post">
					<div align="center">
					
						<label>${practitioner[0]} &nbsp : &nbsp ${practitioner[1]}</label> 

					</div>
				</div>

			</c:forEach>



		</div>
		<hr size="1" width="90%" color="gray">
		
		
		<a href="/Pixel/Patients"> Patients </a>
		
		

	</div>
	
	
	
	
	
</body>
</html>