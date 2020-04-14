<%@ page import="utilities.Helper" %>
<%@ include file="/WEB-INF/public/includes/authMeta.jsp" %>
<body>
<%@ include file="/WEB-INF/public/includes/nav.jsp" %>
<main role="main" class="container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-6 col-lg-11">


            </div>
            <div class="col-sm-9 col-md-6 col-lg-1">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                    Create a user account
                </button>
            </div>
        </div>
    </div>
    <br> <br>
    <%@ include file="/WEB-INF/public/includes/errors.jsp" %>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-6 col-lg-12">
                <c:choose>
                    <c:when test="${listOfTickets.size() > 0}">
                        <h6>
                            Number of AV equipments requests made : (${listOfTickets.size()})
                        </h6>
                        <br>
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>Ticket number</th>
                                <th>Equipment Name</th>
                                <th>Description</th>
                                <th>Created At</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="ticket" items="${listOfTickets}">
                                <tr>
                                    <td>
                                        <a href="open-ticket?id=<c:out value='${ticket.getNumber()}' />">
                                            <c:out value="${ticket.getNumber()}" />
                                        </a>

                                    </td>
                                    <td>
                                        <c:out value="${ticket.getName()}" />

                                    </td>
                                    <td>
                                        <c:out value="${ticket.getDescription()}" />
                                    </td>
                                    <td>
                                        <c:out value="${ticket.getCreatedAt()}" />
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>

                        </table>
                    </c:when>

                    <c:otherwise>
                        <p style="color:red;">No ticket found  !</p>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </div>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Create a user account</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="/register" class="login-form" method="post" id="register_form">
                        <input name="token" type="hidden" value="${sessionScope.csrfToken}" />
                        <div class="form-group">
                            <input name="name" type="text" class="form-control" name="name" placeholder="Name" required/>
                        </div>
                        <div class="form-group">
                            <label for="exampleFormControlInput1">Email address</label>
                            <input name="email" type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@gcu.ac.uk" required>
                        </div>
                        <div class="form-group">
                            <label for="exampleFormControlInput2">User Role</label>
                            <select class="form-control" name="role-of-user" id="exampleFormControlInput2">
                                <option value="ACADEMIC STAFF">ACADEMIC STAFF</option>
                                <option value="GCU AVS STAFF">GCU AVS STAFF</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input name="password" type="password" class="form-control" name="password" placeholder="Password" required/>
                        </div>
                        <div class="form-group">
                            <input name="confirm-password" type="password" class="form-control" name="confirm-password" placeholder="Confirm password" required/>
                        </div>
                        <input type="hidden" name="type-of-submission" value="createNewUser">
                        <button type="submit" class="btn btn-primary">Create</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/bootstrap/js/popper.min.js"></script>
</body>
</html>
