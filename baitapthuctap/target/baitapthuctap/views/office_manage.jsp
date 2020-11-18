<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11/4/2020
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="resources/js/ajax/model/ajax_model_office.js"></script>
<script src="resources/js/ajax/pages/ajax_page_quan_ly_office.js"></script>
<main>
    <!-- Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete Office</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Do You Want To Delete?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger" id="btn-comfirm-delete"><i class="fas fa-trash"></i> Delete
                    </button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add Office</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label for=>Office Code:</label>
                                    <input type="text" class="form-control" id="input-office-code"
                                           placeholder="Enter Office Code">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>City:</label>
                                    <input type="text" class="form-control" id="input-city" placeholder="Enter City">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Phone:</label>
                                    <input type="text" class="form-control" id="input-phone" placeholder="Enter Phone">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Address 1:</label>
                                    <input type="text" class="form-control" id="input-address-1"
                                           placeholder="Enter Address 1">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Address 2:</label>
                                    <input type="text" class="form-control" id="input-address-2"
                                           placeholder="Enter Address 2">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>State:</label>
                                    <input type="text" class="form-control" id="input-state" placeholder="Enter State">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Country:</label>
                                    <input type="text" class="form-control" id="input-country"
                                           placeholder="Enter Country">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Postal Code:</label>
                                    <input type="text" class="form-control" id="input-postal-code"
                                           placeholder="Enter Postal Code">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Territory:</label>
                                    <input type="text" class="form-control" id="input-territory"
                                           placeholder="Enter Territory">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-success" id="btn-comfirm-save">Save</button>
                </div>
            </div>
        </div>
    </div>
    <div class="titlt-page">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <h1>OFFICES</h1>
                </div>
                <div class="col-12">
                    <hr>
                </div>
            </div>
        </div>
    </div>
    <div class="tool-page">
        <div class="container">
            <div class="row">
                <div class="col-md-8 mt-1 mb-1">
                    <button type="button" data-toggle="modal" data-target="#insertModal" class="btn btn-primary"><i
                            class="fas fa-plus" id="btn-add-office"></i> Add Office
                    </button>
                </div>

                <div class="col-md-3 mt-1 mb-1">
                    <input type="text" id="input-text-search" class="form-control">
                </div>
                <div class="col-md-1 mt-1 mb-1 text-center">
                    <button type="button" class="btn btn-primary" id="btn-search"><i class="fas fa-search"></i></button>
                </div>
            </div>
        </div>
    </div>
    <div class="table-data">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="table-reponsive">
                        <table class="table table-hover text-center table-bordered">
                            <thead>
                            <tr>
                                <th scope="col">Office Code</th>
                                <th scope="col">City</th>
                                <th scope="col">Phone</th>
                                <th scope="col">Address 1</th>
                                <th scope="col">Address 2</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td class="text-center">
                                    <button type="button" data-toggle="modal" data-target="#insertModal"
                                            class="btn btn-warning"><i class="fas fa-pencil-alt"></i> Edit
                                    </button>
                                    <button type="button" data-toggle="modal" data-target="#deleteModal"
                                            class="btn btn-danger"><i class="fas fa-trash"></i> Delete
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link btn-prev" id="btn-prev" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>
                <div class="number-page" id="number-page">
<%--                    <li class="page-item"><a class="page-link" href="#">1</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">3</a></li>--%>
                </div>
                <li class="page-item">
                    <a class="page-link btn-next" id="btn-next" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</main>