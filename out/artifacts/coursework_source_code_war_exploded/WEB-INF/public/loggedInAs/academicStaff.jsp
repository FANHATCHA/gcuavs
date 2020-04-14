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
                    Request a AV equipment
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
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="ticket" items="${listOfTickets}">
                                <tr>
                                    <td>
                                        <c:out value="${ticket.getNumber()}" />

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
                                    <td>
                                        <form method="post" action="/ticket" >
                                            <input type="hidden" name="type-of-submission" value="deleteTicket">
                                            <input name="token" id="token" type="hidden" value="${sessionScope.csrfToken}" />
                                            <c:if test="${ticket != null}">
                                                <input type="hidden" name="ticket-id" value="<c:out value='${ticket.id}' />" />
                                            </c:if>
                                            <c:if test="${authUser != null}">
                                                <input type="hidden" name="auth-user-id" value="<c:out value='${authUser.id}' />" />
                                            </c:if>
                                            <button class="btn btn-danger btn-sm" type="submit">Delete</button>
                                        </form>
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
                    <h5 class="modal-title" id="exampleModalLabel">Request a AV equipment</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="/ticket" class="login-form" method="post" id="register_form">
                        <input name="token" type="hidden" value="${sessionScope.csrfToken}" />
                        <div class="form-group">
                            <label for="exampleFormControlInput1">Equipment name</label>
                            <input name="equipment-name" type="text" class="form-control" id="exampleFormControlInput1" placeholder="">
                        </div>

                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">Description</label>
                            <textarea name="description" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                        </div>
                        <c:if test="${authUser != null}">
                            <input type="hidden" name="auth-user-id" value="<c:out value='${authUser.id}' />" />
                        </c:if>
                        <input type="hidden" name="type-of-submission" value="createNewTicket">
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
