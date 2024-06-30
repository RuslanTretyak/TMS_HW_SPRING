<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create User</title>
</head>
<body>
<h4 modelAttribute="model">${message}</h4>
<form:form action="create" method="post" modelAttribute="user">
  <div>
    <form:label path="login"><h3>Enter login:</h3></form:label>
    <form:input path="login" type="text"  value="" />
  </div>
  <div>
    <form:label path="name"><h3>Enter name:</h3></form:label>
    <form:input path="name" type="text"  value="" />
  </div>
  <div>
    <form:label path="surname"><h3>Enter surname:</h3></form:label>
    <form:input path="surname" type="text"  value="" />
  </div>
  <div>
    <form:label path="age"><h3>Enter age:</h3></form:label>
    <form:input path="age" type="number" value="" />
  </div>
  <div>
    <button>Send</button>
  </div>
</form:form>
</body>
</html>