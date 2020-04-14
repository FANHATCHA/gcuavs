<%@ include file="/WEB-INF/public/includes/authMeta.jsp" %>
<body>
<%@ include file="/WEB-INF/public/includes/nav.jsp" %>
<main role="main" class="container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-6 col-lg-11">


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
                                        <a href="manage-ticket?id=<c:out value='${ticket.getNumber()}' />">
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

</main>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/bootstrap/js/popper.min.js"></script>
</body>
</html>
