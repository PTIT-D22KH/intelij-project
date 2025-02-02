<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>User Detail ${user.id}</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Dashboard</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item"> <a href="/admin"> Dashboard </a></li>
              <li class="breadcrumb-item"> <a href="/admin/user"> User </a></li>
              <li class="breadcrumb-item active">User Detail</li>
            </ol>
            <div class="container mt-5">
                <div class="row">
                    <div class="col-12 mx auto">
                        <div class="d-flex justify-content-between">
                            <h3 class="text-center">User detail with id = ${user.id}</h3>
                        </div>
                        <hr/>
                        <div class="card">
                            <div class="card-header" style="width: 60%">
                                <h4>User information</h4>
                            </div>
                            <ul class = "list-group list-group-flush">
                                <li class = "list-group-item">Id: ${user.id}</li>
                                <li class = "list-group-item">Email: ${user.email}</li>
                                <li class = "list-group-item">Fullname: ${user.fullName}</li>
                                <li class = "list-group-item">Phone Number: ${user.phone}</li>
                                <li class = "list-group-item">Address: ${user.address}</li>
                                
                            </ul>
                        </div>
                        <a class="btn btn-success mt-3" href = '/admin/user'>Back</a>
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