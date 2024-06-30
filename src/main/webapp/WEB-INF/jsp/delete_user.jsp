<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Delete User</title>
</head>
<body>
    <form:form action="delete" method="post" modelAttribute="user">
        <h4>${message}</h4>
        <div>
          <form:label path="id"><h3>Enter id:</h3></form:label>
          <form:input path="id" type="number" id="id" value="" />
        </div>
        <div>
          <button>Send</button>
        </div>
      </form:form>
</body>
</html>