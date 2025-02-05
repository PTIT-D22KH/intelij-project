<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Create Product - Hoi Dân IT</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>

            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Products</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Product</li>
                                </ol>
                                <div class="mt-3">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <form:form method="post" action="/admin/product/create"
                                                modelAttribute="newProduct" class="row g-2"
                                                enctype="multipart/form-data">
                                                <!-- khai báo biến -->
                                                <c:set var="errorName">
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </c:set>

                                                <c:set var="errorPrice">
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </c:set>

                                                <c:set var="errordetailDesc">
                                                    <form:errors path="detailDesc" cssClass="invalid-feedback" />
                                                </c:set>

                                                <c:set var="errorShortDesc">
                                                    <form:errors path="shortDesc" cssClass="invalid-feedback" />
                                                </c:set>

                                                <c:set var="errorQuantity">
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </c:set>


                                                <h3>Create a product</h3>
                                                <hr>
                                                <div class="col-md-6  col-12">

                                                    <label for="name" class="form-label">Name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                        id="name" path="name" />
                                                    ${errorName}
                                                </div>
                                                <div class="col-md-6  col-12">

                                                    <label for="price" class="form-label">Price:</label>
                                                    <form:input type="number"
                                                        class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                        id="price" path="price" />
                                                    ${errorPrice}
                                                </div>
                                                <div class=" col-12">

                                                    <label for="detailDesc"
                                                        class="form-label  ${not empty errordetailDesc ? 'is-invalid' : ''}">
                                                        Detail description:</label>
                                                    <form:textarea rows="3" cols="63" id="detailDesc"
                                                        path="detailDesc" />
                                                    ${errordetailDesc}
                                                </div>
                                                <div class="col-md-6  col-12">

                                                    <label for="shortDesc" class="form-label">Short description:</label>
                                                    <form:input type="text"
                                                        class="form-control  ${not empty errorShortDesc ? 'is-invalid' : ''}"
                                                        id="shortDesc" path="shortDesc" />
                                                    ${errorShortDesc}
                                                </div>

                                                <div class="col-md-6 col-12">

                                                    <label for="quantity" class="form-label">Quantity:</label>
                                                    <form:input type="number"
                                                        class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}"
                                                        id="quantity" path="quantity" />
                                                    ${errorQuantity}
                                                </div>
                                                <div class="col-md-6  col-12">
                                                    <label for="inputRole" class="form-label">Factory:</label>
                                                    <form:select id="inputRole" class="form-select" path="factory">
                                                        <form:option value="APPLE">Apple (Macbook)</form:option>
                                                        <form:option value="ASUS">Asus</form:option>
                                                        <form:option value="LENOVO">Lenovo</form:option>
                                                        <form:option value="DELL">Dell</form:option>
                                                        <form:option value="LG">LG</form:option>
                                                        <form:option value="ACER">Acer</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-6  col-12">
                                                    <label for="target" class="form-label">Target:</label>
                                                    <form:select id="target" class="form-select" path="target">
                                                        <form:option value="GAMING">Gaming</form:option>
                                                        <form:option value="SINHVIEN-VANPHONG">Sinh Viên - Văn phòng
                                                        </form:option>
                                                        <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa
                                                        </form:option>
                                                        <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                                        <form:option value="DOANH-NHAN">Doanh nhân</form:option>

                                                    </form:select>
                                                </div>

                                                <div class="col-md-6  col-12">
                                                    <label for="avatarFile" class="form-label">Image:
                                                    </label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                                                </div>
                                                <div class="col-12 ">
                                                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                        id="avatarPreview" />
                                                </div>
                                                <div class="col-12 mt-5">
                                                    <button type="submit" class="btn btn-primary">Create</button>
                                                    <a href="/admin/product"><button type="button"
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