<%@ include file="/WEB-INF/public/includes/meta.jsp" %>

<body>
    <form action="/user-auth" class="form-signin" method="post">
    <div class="text-center mb-4">
        <img class="mb-4" src="assets/img/gcu_logo.PNG" alt="" width="70" height="45">
        <h1 class="h3 mb-3 font-weight-normal">GCU AVS</h1>
        <p>
            Log In
        </p>
    </div>
        <%@ include file="/WEB-INF/public/includes/errors.jsp" %>
    <div class="form-label-group">
        <input name="email" type="text" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputEmail">Email address</label>
    </div>

    <div class="form-label-group">
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <label for="inputPassword">Password</label>
    </div>

    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <p class="mt-5 mb-3 text-muted text-center">&copy; GCUAVS - 2020</p>
</form>
</body>
</html>
