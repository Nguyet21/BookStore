<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>BookStore Tracker App</title>
	
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<%@ include file = "/header/header.jsp" %>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add BookStore -->
			
			<a href="AuthorServletController?command=NEW">Add new Author</a>
			<table>
			
				<tr>
					<th>Author Name</th>
					<th>DOB</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempAuthor" items="${AUTHOR_LIST}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="AuthorServletController">
						<c:param name="command" value="LOAD" />
						<c:param name="authorId" value="${tempAuthor.id}" />
					</c:url>

					<c:url var="deleteLink" value="AuthorServletController">
						<c:param name="command" value="DELETE" />
						<c:param name="authorId" value="${tempAuthor.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempAuthor.name} </td>
						<td> ${tempAuthor.dobString} </td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this author?'))) return false">
							Delete</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








