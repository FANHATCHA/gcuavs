<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="/">GCU AVS</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="javascript:;" onClick="window.location.reload();">Home <span class="sr-only">(current)</span></a>
            </li>
            <c:if test='${userRole == "STAFF_ADMIN"}'>
                <li class="nav-item">
                    <a class="nav-link" href="/ticket-list">Tickets</a>
                </li>
            </c:if>

        </ul>
        <a class="nav-link" href="#">Logged In as:  <c:if test="${userRole != null}"><c:out value='${userRole}' /></c:if></a>
        <div class="dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
                <c:if test="${authUser != null}"><c:out value='${authUser.name}' /></c:if>
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#">Edit profile</a>
<%--                <a class="dropdown-item" href="#">--%>
<%--                    Log Out </a>--%>
                    <form action="/logout" method="post">
                        <a class="dropdown-item" href="javascript:;" onclick="parentNode.submit();">Log Out</a>
                        <input type="hidden" name="mess" value="Logout"/>
                    </form>

            </div>
        </div>
    </div>
</nav>