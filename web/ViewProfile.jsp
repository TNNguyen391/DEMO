<%-- 
    Document   : viewProfile
    Created on : 16/09/2024, 12:04:19 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://unpkg.com/bs-brain@2.0.4/components/registrations/registration-7/assets/css/registration-7.css">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Registration 7 - Bootstrap Brain Component -->
<section class="bg-light p-3 p-md-4 p-xl-5">
    <div class="container">
        <a href="HomePageJSP.jsp">Back to main page</a>
        <div class="row justify-content-center">
            <div class="col-12 col-md-9 col-lg-7 col-xl-6 col-xxl-5">
                <div class="card border border-light-subtle rounded-4">
                    <div class="card-body p-3 p-md-4 p-xl-5">
                        <div class="row">
                            <div class="col-12">
                                <div class="mb-5">
                                    <div class="text-center mb-4">
                                        <a href="#!">
                                            <img src="img/logo.png" alt="Logo" width="75" height="75">
                                        </a>
                                    </div>
                                    <h2 class="h4 text-center">View profile</h2>
                                    <h3 class="fs-6 fw-normal text-secondary text-center m-0">You can update your profile here</h3>
                                </div>
                            </div>
                        </div>
                        <form action="updateAction" method="POST">
                            <div class="row gy-3 overflow-hidden">

                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" name="txtFullName" id="fullName" value="${sessionScope.USER.fullName}" readonly>
                                        <label for="fullName" class="form-label">Full Name</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" name="txtEmail" id="email" value="${sessionScope.EMAIL}" readonly>
                                        <label for="email" class="form-label">Email</label>
                                    </div>
                                </div>

                                <c:set var="errors" value="${requestScope.CREATE_ERROR}"/>
                                <c:if test="${sessionScope.CONFIRM_FLAG == null}">
                                    <%--
                                    <c:if test="${not empty errors.emailFormat}">
                                        <font color="red">
                                        ${errors.emailFormat}
                                        </font> <br/>
                                    </c:if> 
                                    --%>
                                    <div class="col-12">
                                        <label class="form-label text-secondary">Gender:</label>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" value="Male" name="txtGender" id="male" required
                                                   <c:if test="${sessionScope.GENDER == 'Male'}">
                                                       checked="checked"
                                                   </c:if> />
                                            <label class="form-check-label text-secondary" for="male">
                                                Male
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" value="Female" name="txtGender" id="female" required
                                                   <c:if test="${sessionScope.GENDER == 'Female'}">
                                                       checked="checked"
                                                   </c:if> />
                                            <label class="form-check-label text-secondary" for="female">
                                                Female
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" value="Hidden" name="txtGender" id="hidden" required
                                                   <c:if test="${sessionScope.GENDER == 'Hidden'}">
                                                       checked="checked"
                                                   </c:if> />
                                            <label class="form-check-label text-secondary" for="hidden">
                                                Hidden
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name="txtPhone" id="phone" value="${sessionScope.PHONE}" >
                                            <label for="phone" class="form-label">Phone</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.phonelength}">
                                        <font color="red">
                                        ${errors.phonelength}
                                        </font> <br/>
                                    </c:if>
                                    <c:if test="${not empty errors.phoneEmpty}">
                                        <font color="red">
                                        ${errors.phoneEmpty}
                                        </font> <br/>
                                    </c:if>
                                    <c:if test="${not empty errors.phoneExisted}">
                                        <font color="red">
                                        ${errors.phoneExisted}
                                        </font> <br/>
                                    </c:if>

                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name="txtStreet" id="address" value="${sessionScope.STREET}" >
                                            <label for="street" class="form-label">Street</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.streetFormat}">
                                        <font color="red">
                                        ${errors.streetFormat}
                                        </font> <br/>
                                    </c:if> 
                                    <c:if test="${not empty errors.streetEmpty}">
                                        <font color="red">
                                        ${errors.streetEmpty}
                                        </font> <br/>
                                    </c:if>

                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name="txtCity" id="address" value="${sessionScope.CITY}" >
                                            <label for="city" class="form-label">City</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.cityFormat}">
                                        <font color="red">
                                        ${errors.cityFormat}
                                        </font> <br/>
                                    </c:if> 
                                    <c:if test="${not empty errors.cityEmpty}">
                                        <font color="red">
                                        ${errors.cityEmpty}
                                        </font> <br/>
                                    </c:if>

                                    <%--
                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="password" class="form-control" name="txtPassword" id="password" value="************" placeholder="Password" required>
                                            <label for="password" class="form-label">Password</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.passwordLength}">
                                        <font color="red">
                                        ${errors.passwordLength}
                                        </font> <br/>
                                    </c:if> 
                                    <c:if test="${not empty errors.passwordFormat}">
                                        <font color="red">
                                        ${errors.passwordFormat}
                                        </font> <br/>
                                    </c:if> 
                                    --%>
                                </c:if>

                                <c:if test="${sessionScope.CONFIRM_FLAG != null}">
                                    <%--
                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="email" class="form-control" name="txtEmail" id="email" value="${param.txtEmail}" placeholder="name@example.com" required>
                                            <label for="email" class="form-label">Email</label>
                                        </div>
                                    </div>
                                    --%>
                                    <div class="col-12">
                                        <label class="form-label text-secondary">Gender:</label>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" value="Male" name="txtGender" id="male" required
                                                   <c:if test="${param.txtGender == 'Male'}">
                                                       checked="checked"
                                                   </c:if> />
                                            <label class="form-check-label text-secondary" for="male">
                                                Male
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" value="Female" name="txtGender" id="female" required
                                                   <c:if test="${param.txtGender == 'Female'}">
                                                       checked="checked"
                                                   </c:if> />
                                            <label class="form-check-label text-secondary" for="female">
                                                Female
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" value="Hidden" name="txtGender" id="hidden" required
                                                   <c:if test="${param.txtGender == 'Hidden'}">
                                                       checked="checked"
                                                   </c:if> />
                                            <label class="form-check-label text-secondary" for="hidden">
                                                Hidden
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name="txtPhone" id="phone" value="${param.txtPhone}" >
                                            <label for="phone" class="form-label">Phone</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.phonelength}">
                                        <font color="red">
                                        ${errors.phonelength}
                                        </font> <br/>
                                    </c:if> 
                                    <c:if test="${not empty errors.phoneEmpty}">
                                        <font color="red">
                                        ${errors.phoneEmpty}
                                        </font> <br/>
                                    </c:if>
                                    <c:if test="${not empty errors.phoneExisted}">
                                        <font color="red">
                                        ${errors.phoneExisted}
                                        </font> <br/>
                                    </c:if>


                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name="txtStreet" id="address" value="${param.txtStreet}" >
                                            <label for="street" class="form-label">Street</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.streetFormat}">
                                        <font color="red">
                                        ${errors.streetFormat}
                                        </font> <br/>
                                    </c:if> 
                                    <c:if test="${not empty errors.streetEmpty}">
                                        <font color="red">
                                        ${errors.streetEmpty}
                                        </font> <br/>
                                    </c:if>

                                    <div class="col-12">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name="txtCity" id="address" value="${param.txtCity}" >
                                            <label for="city" class="form-label">City</label>
                                        </div>
                                    </div>
                                    <c:if test="${not empty errors.cityFormat}">
                                        <font color="red">
                                        ${errors.cityFormat}
                                        </font> <br/>
                                    </c:if>
                                    <c:if test="${not empty errors.cityEmpty}">
                                        <font color="red">
                                        ${errors.cityEmpty}
                                        </font> <br/>
                                    </c:if>

                                    <%--
                            <div class="col-12">
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" name="txtPassword" id="password" value="${param.txtPassword}" placeholder="Password" required>
                                    <label for="password" class="form-label">Password</label>
                                </div>
                            </div>
                                    --%>
                                </c:if>
                                <%--
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="password" class="form-control" name="txtPassword2" id="password" value="${param.txtPassword2}" placeholder="Password" required>
                                        <label for="password" class="form-label">Confirm Password</label>
                                    </div>
                                    <c:if test="${not empty errors.confirmNotMatched}">
                                        <font color="red">
                                        ${errors.confirmNotMatched}
                                        </font> <br/>
                                    </c:if>
                                </div>
                                --%>
                                <div class="col-12" >
                                    <div style="text-align: right">
                                        <button class="btn bsb-btn-xl btn-primary" type="submit">Save</button><br>
                                        <input type="hidden" name="txtFullname" value="${sessionScope.USER.fullName}" readonly="readonly" />
                                        <font style="color: brown">
                                        <c:if test="${sessionScope.CONFIRM_FLAG != null}">
                                            ${sessionScope.CONFIRM_FLAG}
                                        </c:if>
                                        <font/>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
