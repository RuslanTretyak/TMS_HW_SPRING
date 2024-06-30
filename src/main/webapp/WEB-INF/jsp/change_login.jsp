<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Change Login</title>
</head>
<body>
<form:form action="change" method="post" modelAttribute="user">
  <h4>${message}</h4>
  <div>
    <form:label path="id"><h3>Enter id:</h3></form:label>
    <form:input path="id" type="number" id="id" value="" />
  </div>
  <div>
    <form:label path="login"><h3>Enter new login:</h3></form:label>
    <form:input path="login" type="text" id="login" value="" />
  </div>
  <div>
    <button>Send</button>
  </div>
</form:form>
</body>
</html>