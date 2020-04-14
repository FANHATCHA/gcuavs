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
                <div class="container-fluid">
                    <h5>Ticket # <span style="color:#007bff"><c:out value="${ticket.getNumber()}" /></span> </h5>
                    <hr> <br>
                    <div class="row">
                        <div class="col-sm-3 col-md-6 col-lg-4">
                            <h5>
                                Summary of ticket #<c:if test="${ticket != null}"><c:out value='${ticket.getNumber()}' /></c:if>
                                <br>
                                Ticket Priority:
                                <c:forEach var="priority" items="${listOfPriorities}">
                                    <b>
                                        <c:if test="${priority.getTicketNumber() == ticket.getNumber()}">
                                        <c:choose>
                                            <c:when test='${priority.getPriority() == "LOW"}'>
                                                <span style="color:#27ae60;">
                                                  <c:out value='${priority.getPriority()}' />
                                                </span>
                                            </c:when>
                                            <c:when test='${priority.getPriority() == "MEDIUM"}'>
                                                <span style="color:#e67e22;">
                                                  <c:out value='${priority.getPriority()}' />
                                                </span>
                                            </c:when>
                                            <c:when test='${priority.getPriority() == "HIGH"}'>
                                                 <span style="color:#e74c3c">
                                                  <c:out value='${priority.getPriority()}' />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <i>Not set yet !</i>
                                            </c:otherwise>
                                        </c:choose>
                                        </c:if>
                                    </b>
                                </c:forEach>
                            </li>
                            </h5>
                            <ul>
                                <li>Equipment name: <c:if test="${ticket != null}"><c:out value='${ticket.getName()}' /></c:if></li>
                                <li>Equipment description: <c:if test="${ticket != null}"><c:out value='${ticket.getDescription()}' /></c:if></li>
                                <li>Requester name: <c:if test="${ticket != null}"><c:out value='${ticket.getRequesterName()}' /></c:if></li>
                                <li>Ticket assigned to :
                                <c:forEach var="user" items="${listOfUsers}">
                                    <b><c:if test="${user.getId() == ticket.getAssignedTo()}"><c:out value='${user.getName()}' /></c:if></b>
                                </c:forEach>
                            </li>
                                <li>Ticket Status :
                                    <c:forEach var="status" items="${listOfStatus}">
                                        <b><c:if test="${ticket.getNumber()== status.getTicketNumber()}"><c:out value='${status.getProgress()}' /></c:if></b>
                                    </c:forEach>
                                </li>
                                <li>Created At: <c:if test="${ticket != null}"><c:out value='${ticket.getCreatedAt()}' /></c:if></li>
                            </ul>
                        </div>
                        <div class="col-sm-9 col-md-6 col-lg-8">

                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>Assign ticket to:</th>
                                            <th>Set ticket priority</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                <form method="post" action="/ticket" >
                                                    <input name="token" id="token" type="hidden" value="${sessionScope.csrfToken}" />
                                                    <select class="custom-select" name="assigned-to-id" id="assigned-to-id" onchange='this.form.submit()'>
                                                        <option value="" disabled selected>Choose an AVS staff</option>
                                                        <c:forEach var="user" items="${listOfUsers}">
                                                            <c:if test="${user.getRoleID() == 2}">
                                                                <option value="<c:out value='${user.getId()}' />"><c:out value='${user.getName()}' /></option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="hidden" name="type-of-submission" value="assignTicketTo">
                                                    <c:if test="${authUser != null}">
                                                        <input type="hidden" name="auth-user-id" value="<c:out value='${authUser.id}' />" />
                                                    </c:if>
                                                    <c:if test="${ticket != null}">
                                                        <input type="hidden" name="ticket-number" value="<c:out value='${ticket.getNumber()}' />" />
                                                    </c:if>

                                                    <noscript><input type="submit" value="Submit"/></noscript>
                                                </form>

                                                </td>
                                                <td>
                                                    <form method="post" action="/ticket" >
                                                        <input name="token" id="token" type="hidden" value="${sessionScope.csrfToken}" />
                                                        <select class="custom-select" name="priority-assigned" onchange='this.form.submit()'>
                                                            <option value="" disabled selected>Choose a priority</option>
                                                            <option value="LOW">LOW</option>
                                                            <option value="MEDIUM">MEDIUM</option>
                                                            <option value="HIGH">HIGH</option>
                                                        </select>
                                                        <input type="hidden" name="type-of-submission" value="assignPriority">
                                                        <c:if test="${ticket != null}">
                                                            <input type="hidden" name="ticket-number" value="<c:out value='${ticket.getNumber()}' />" />
                                                        </c:if>

                                                        <noscript><input type="submit" value="Submit"/></noscript>
                                                    </form>

                                                </td>
                                            </tr>
                                        </tbody>

                                    </table>
                        </div>
                    </div>
                </div>
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
