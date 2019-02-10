<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<title>BookStore Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<%@ include file = "../header/header.jsp" %>
	
	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add BookStore -->
			
			Top Revenue By Author
			
			<table>
			
				<tr>
					<th>Author Name</th>
					<th>Revenue</th>
					
				</tr>
				
				<c:forEach var="tempCategory" items="${author_List}">
					<tr>
						<td> ${tempCategory.name} </td>
						<td> ${tempCategory.revenue}</td>
					</tr>
				
				</c:forEach>
				
			</table>
			
			
			
		
		</div>
	
	</div>
</body>


</html>
