<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="" />
                    <meta name="author" content="" />
                    <title>Update User - Hoi Dân IT</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                </head>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        const orgImage = "${newUser.avatar}";
                        if (orgImage) {
                            const urlImage = "/images/avatar/" + orgImage;
                            $("#avatarPreview").attr("src", urlImage);
                            $("#avatarPreview").css({ "display": "block" });
                        }

                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Manage Users</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item active">Users</li>
                                        <li class="breadcrumb-item active">Update</li>
                                    </ol>
                                    <div class="mt-5">
                                        <div class="row">
                                            <div class="col-md-6 col-12 mx-auto">
                                                <form:form method="post" action="/admin/user/update"
                                                    modelAttribute="newUser" enctype="multipart/form-data">
                                                    <!-- validate cột full name -->
                                                    <c:set var="errorFullName">
                                                        <form:errors path="fullName" cssClass="invalid-feedback" />
                                                    </c:set>

                                                    <h3>Update a user</h3>
                                                    <hr>
                                                    <div class="mb-3">
                                                        <div class="mb-3" style="display: none;">
                                                            <label for="id" class="form-label">Id:</label>
                                                            <form:input type="text" class="form-control" id="id"
                                                                path="id" />
                                                        </div>
                                                        <label for="email" class="form-label">Email:</label>
                                                        <form:input type="email" class="form-control" id="email"
                                                            path="email" disabled="true" />
                                                    </div>

                                                    <div class="mb-3">
                                                        <label for="phone" class="form-label">Phone number:</label>
                                                        <form:input type="text" class="form-control" id="phone"
                                                            path="phone" />
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="fullName" class="form-label">Full name:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorFullName ? 'is-invalid' : ''}"
                                                            id="fullName" path="fullName" />
                                                        ${errorFullName}
                                                    </div>


                                                    <div class="col-md-6  col-12">
                                                        <label for="inputRole" class="form-label">Role:</label>
                                                        <form:select id="inputRole" class="form-select"
                                                            path="role.name">
                                                            <c:choose>
                                                                <c:when
                                                                    test="${newUser.getRole().getName() eq 'ADMIN'}">
                                                                    <option value="ADMIN" selected>Admin
                                                                    </option>
                                                                    <option value="USER">User</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="ADMIN">Admin</option>
                                                                    <option value="USER" selected>User
                                                                    </option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </form:select>
                                                    </div>


                                                    <div class="mb-3">
                                                        <label for="address" class="form-label">Address:</label>
                                                        <form:input type="text" class="form-control" id="address"
                                                            path="address" />
                                                    </div>


                                                    <div class="col-md-6  col-12">
                                                        <label for="avatarFile" class="form-label">Avatar:
                                                        </label>
                                                        <input class="form-control" type="file" id="avatarFile"
                                                            accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                                                    </div>
                                                    <div class="col-12 ">
                                                        <c:choose>
                                                            <c:when test="${newUser.getAvatar() == null}">
                                                                <img style="max-height: 250px; display: none;"
                                                                    alt="avatar preview" id="avatarPreview" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img style="max-height: 250px; display: block;"
                                                                    alt="avatar preview" id="avatarPreview"
                                                                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-YIGV8GTRHiW_KACLMhhi9fEq2T5BDQcEyA&usqp=CAU" />

                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>


                                                    <div class="col-12 mt-5">
                                                        <button type="submit" class="btn btn-warning">Update</button>
                                                        <a href="/admin/user"><button type="button"
                                                                class="btn btn-secondary mx-3">Cancel</button></a>
                                                    </div>
                                                </form:form>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </main>

                            <jsp:include page="../layout/footer.jsp" />

                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="js/scripts.js"></script>
                </body>

                </html>