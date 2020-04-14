<%@ include file="/WEB-INF/public/includes/authMeta.jsp" %>
<body>
<%@ include file="/WEB-INF/public/includes/nav.jsp" %>
<main role="main" class="container">
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
                        <div class="col-sm-9 col-md-6 col-lg-4">
                         Add a comment to ticket <br><hr>
                            <form method="post" action="/comment">
                                <input name="token" type="hidden" value="${sessionScope.csrfToken}" />
                                <div class="form-group">
                                    <label for="exampleFormControlTextarea1">Example textarea</label>
                                    <textarea id="comment" name="comment" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                                </div>
                                <c:if test="${ticket != null}">
                                    <input type="hidden" name="ticket-number" value="<c:out value='${ticket.getNumber()}' />" />
                                </c:if>
                                <input type="hidden" name="type-of-submission" value="add-comment">
                                <button type="submit" class="btn btn-primary">Comment</button>
                            </form>
                            <hr>

                            <c:choose>
                                <c:when test="${listOfComments.size() > 0}">
                                    <b>List Of comments</b>
                                    <hr>
                                    <br>
                                        <c:forEach var="comment" items="${listOfComments}">
                                            <h6>
                                                <b> <c:out value="${comment.getName()}" /></b>:   <c:out value="${comment.getComment()}" />
                                            </h6>
                                            <b>Timestamp: </b> <c:out value="${comment.getCreatedAt()}" />
                                            <hr>
                                        </c:forEach>

                                </c:when>
                                <c:otherwise>
                                    <p style="color:red;">No comment found  !</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-sm-9 col-md-6 col-lg-4">
                            Set ticket status <br><hr>
                            <form method="post" action="/comment" >
                                <input name="token" id="token" type="hidden" value="${sessionScope.csrfToken}" />
                                <select class="custom-select" name="progress" onchange='this.form.submit()'>
                                    <option value="" disabled selected>Choose a status</option>
                                    <option value="Open">Open</option>
                                    <option value="In-progress">In-progress</option>
                                    <option value="Closed">Closed</option>
                                </select>
                                <input type="hidden" name="type-of-submission" value="add-status">
                                <c:if test="${ticket != null}">
                                    <input type="hidden" name="ticket-number" value="<c:out value='${ticket.getNumber()}' />" />
                                </c:if>
                                <noscript><input type="submit" value="Submit"/></noscript>
                            </form>
                        </div>
                    </div>
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
