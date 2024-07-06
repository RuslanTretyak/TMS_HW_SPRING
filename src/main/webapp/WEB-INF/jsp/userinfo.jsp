<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
</head>
<body>
    <c:if test="${isReqSuccessful}">
        <h4>Name: ${name}</h4>
        <h4>Surname: ${surname}</h4>
        <h4>Login: ${login}</h4>
        <h4>Age: ${age}</h4>
    </c:if>
    <c:if test="${!isReqSuccessful}">
        <h2>${message}</h2>
        
    </c:if>
    

</body>
</html>