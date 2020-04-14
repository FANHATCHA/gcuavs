<c:if test="${NOTIFICATION != null}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ${NOTIFICATION}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>

<c:if test="${ERROR_NOTE != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${ERROR_NOTE}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>

<c:if test="${errors != null}">
    <c:if test="${errors.size() != 0}">
        <c:forEach items="${errors}" var="error">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:forEach>
    </c:if>
</c:if>