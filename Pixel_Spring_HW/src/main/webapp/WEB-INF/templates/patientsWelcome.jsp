<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Patients</title>
</head>
<body>
	
	<label>Patients</label>
	
	<div align="center">
	
		<form method="get" action="Patients">
			<label>list of cities: &nbsp </label> 
			<input name="cities" type="text" placeholder="city, city, ..." />
			<label>list of specializations: &nbsp </label> 
			<input name="specializations" type="text" placeholder="spec, spec, ..." />
			
			
			<input type="submit" value="Szukaj" />
		</form>
		</br>
		<label>First name &nbsp : &nbsp Second name &nbsp : &nbsp Number of Visits </label> 

		<hr size="1" width="90%" color="gray">

		<div>
			<c:forEach var="patient" items="${patients}">

				<div id="post">
					<div align="center">

						<label>${patient[0]} &nbsp : &nbsp ${patient[1]} &nbsp : &nbsp ${patient[2]}</label> 

					</div>
				</div>

			</c:forEach>



		</div>
		<hr size="1" width="90%" color="gray">
		
		<a href="/Pixel/Practitioners"> Practitioners </a>

	</div>
	
	
	
</body>
</html>