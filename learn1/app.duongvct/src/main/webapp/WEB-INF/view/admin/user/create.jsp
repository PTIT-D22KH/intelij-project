<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create a user</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6 col-12 mx auto">
                <h3 class="text-center">Create a user</h3>
                <hr>
                <form:form method="post" action="/admin/user/create" modelAttribute="newUser">
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <form:input type="email" class="form-control" path="email" />
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Password</label>
                        <form:input type="password" class="form-control" path="password"/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Phone Number</label>
                        <form:input type="text" class="form-control" path="phone"/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Full Name</label>
                        <form:input type="text" class="form-control" path="fullName"/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Address</label>
                        <form:input type="text" class="form-control" path="address"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Create</button>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>