package com.minhhai.thuctap.controller;

import com.google.gson.Gson;
import com.minhhai.thuctap.model.JsonResult;
import com.minhhai.thuctap.model.Office;
import com.minhhai.thuctap.service.OfficeService;
import com.minhhai.thuctap.service_impl.OfficeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OfficeController", urlPatterns = "/api/v1/office/*")
public class OfficeController extends HttpServlet {
    private OfficeService officeService = new OfficeServiceImpl();

    private JsonResult jsonResult = new JsonResult();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //find-by-id
        //find-all
        String pathInfor = request.getPathInfo();
        String rs = "";
        if(pathInfor.indexOf("/find-all") == 0) {
            try {
                List<Office> categoryList = officeService.findAll();
//                rs = categoryList == null ? null :categoryList.toString();
                rs = jsonResult.jsonSuccess(categoryList);
            } catch (Exception e) {
                e.printStackTrace();
//                rs = "find all error";
                rs = jsonResult.jsonFail("find all error");
            }
            response.getWriter().write(rs);
            //gọi điện service find all
        } else if(pathInfor.indexOf("/find-by-id") == 0) {
            String id = String.valueOf(request.getParameter("id"));
            try {
                Office office = officeService.findById(id);
                rs = jsonResult.jsonSuccess(office == null ? "Không tìm thấy office tương ứng" : office);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find by id error");
            }
            response.getWriter().write(rs);
            //gọi đến service find by id
        }else if(pathInfor.indexOf("/get-office-from-to") == 0) {
            int  page = Integer.parseInt(request.getParameter("page"));
            int  itemPerPage = 5;
            int  startPage = (page - 1) * itemPerPage;
            try {
                List<Office> officeList = officeService.getOfficeFromTo(startPage,itemPerPage);
                rs = jsonResult.jsonSuccess(officeList);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find all error");
            }
            response.getWriter().write(rs);
        } else if (pathInfor.indexOf("/search") == 0){
            String text = String.valueOf(request.getParameter("text"));
            try {
                List<Office> categoryList = officeService.search(text);
//                rs = categoryList == null ? null :categoryList.toString();
                rs = jsonResult.jsonSuccess(categoryList);
            } catch (Exception e) {
                e.printStackTrace();
//                rs = "find all error";
                rs = jsonResult.jsonFail("search error");
            }
            response.getWriter().write(rs);
        }else {
            response.sendError(404, "Url is not supported");
        }
    }

    //thực hiện đối với các chức năng thêm category
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rs = "";
        try {
            //Client sẽ gửi một chuỗi có định dạng json lên cho server
            //chuỗi json phải có định dạng tương ứng với một đối tượng category
            //sử dụng gson để chuyển chuỗi json sang đối tượng category
            //thực hiện lấy trường name bằng getName truyền vào cho hàm insert

            //sử dụng fromJson để chuyển dữ liệu client truyền lên cho server thành
            //một đối tượng tượng tham sô của hàm nãy tương ứng là:
            //tham số đầu thì là một bộ đêm để đọc chuỗi hoặc là một chuỗi
            //Tham số thứ hai là class muốn chuyển từ json về
            Office office = new Gson().fromJson(request.getReader(),Office.class);
            Office newOffice = officeService.insert(office);
            rs = newOffice != null ? jsonResult.jsonSuccess(newOffice) :
                    jsonResult.jsonSuccess("Them office thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("upload office fail!");
        }
        response.getWriter().write(rs);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try {
            Office category = new Gson().fromJson(req.getReader(), Office.class);
            rs = jsonResult.jsonSuccess(officeService.update(category));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("update office fail!");
        }
        resp.getWriter().write(rs);
    }

    //thực hiện với các chức năng xóa category
//    @RequestMapping(value = "delete-office",params = "id", method = RequestMethod.DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try {
            String id = String.valueOf(req.getParameter("id"));
            System.out.println(id);
            rs = jsonResult.jsonSuccess(officeService.delete(id));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("delete office fail");
        }
        resp.getWriter().write(rs);
    }

}
