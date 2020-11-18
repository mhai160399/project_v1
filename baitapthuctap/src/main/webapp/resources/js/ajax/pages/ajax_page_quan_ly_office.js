let btnAddOffice, btnComfirmDelete, btnComfirmSave, btnSearch, btnNext, btnPrev;
let inputOfficeCode, inputCity, inputPhone, inputAddress1, inputAddress2, inputSate, inputCountry, inputPostalCode, inputTerritory, inputTextSearch;
let indexOffice, elementOffice;
var tableDuLieu;
var numberPage;
let listOffice = null;
let perPage = 5;
let currentPage = 1;
let start = 0;
let end = perPage;
// let totalPage = 0;
// const a = totalPage;

$(async function (){
    btnAddOffice = $("#btn-add-office");
    btnComfirmDelete = $("#btn-comfirm-delete");
    btnComfirmSave = $("#btn-comfirm-save");
    btnSearch = $("#btn-search");
    btnNext = $(".btn-next");
    btnPrev = $(".btn-prev");
    inputOfficeCode = $("#input-office-code");
    inputCity = $("#input-city");
    inputPhone = $("#input-phone");
    inputAddress1 = $("#input-address-1");
    inputAddress2 = $("#input-address-2");
    inputSate = $("#input-state");
    inputCountry = $("#input-country");
    inputPostalCode = $("#input-postal-code");
    inputTerritory = $("#input-territory");
    inputTextSearch = $("#input-text-search");
    tableDuLieu = $("tbody");
    numberPage = $("number-page")
    layTatCaOffice();
    xuLyTimKiem();
    themOffice();
    xacNhanXoaOffice();
    luuOffice();

})

function viewDanhSachOffice(){
    let view = "<tr><td colspan='3'>Khong co du lieu tuong ung</td></tr>";
    if (listOffice && listOffice.length > 0){
        view = listOffice.map((data,index)=>{
            if (index >= start && index < end){
                return `<tr data-index="${index}">
                        <th scope="row">${viewField(data.officeCode)}</th>
                        <td>${viewField(data.city)}</td>
                        <td>${viewField(data.phone)}</td>
                        <td>${viewField(data.addressLine1)}</td>
                        <td>${viewField(data.addressLine2)}</td>
                        <td class="text-center">
                            <button type="button" data-toggle="modal" data-target="#insertModal" class="btn btn-warning sua-office"><i class="fas fa-pencil-alt"></i> Edit</button>
                            <button type="button" data-toggle="modal" data-target="#deleteModal" class="btn btn-danger xoa-office"><i class="fas fa-trash"></i> Delete</button>
                        </td>
                    </tr>`;
            }

        }).join("");
    }
    tableDuLieu.html(view);
    suaOffice();
    xoaOffice();

}


async function layTatCaOffice(){
    console.log("vao ham lay tat ca")
    await officeFindAll().then(rs => {
        if (rs.message == "success"){
            console.log(rs)
            listOffice = rs.data;
        }else {
            listOffice = [];
        }
    }).catch(err => {
        console.log(err)
    })
    const totalPage = Math.ceil(listOffice.length / perPage);
    await viewDanhSachOffice()
    renderListPage(totalPage);
    tienTrang(totalPage);
    luiTrang(totalPage);
}
async function timKiemOffice(valSearchText){
    await searchOffice(valSearchText).then(rs => {
        if (rs.message == "success"){
            console.log(rs)
            listOffice = rs.data;
        }else {
            listOffice = [];
        }
    }).catch(err => {
        console.log(err)
    })
     viewDanhSachOffice();
    const totalPage = Math.ceil(listOffice.length / perPage);
    await viewDanhSachOffice()
    renderListPage(totalPage);
    tienTrang(totalPage);
    luiTrang(totalPage);
}

function xuLyTimKiem() {
    btnSearch.click(
        async function () {
        let valSearchText = inputTextSearch.val();
        console.log("vao ham tim kiem")
            console.log(valSearchText)
            if (valSearchText == ""){
                layTatCaOffice();
            }else {
                timKiemOffice(valSearchText);
            }
    })
}

function xoaOffice() {
    //xóa có 2 bước/
    //click xóa office thì sẽ thực hiện chức nằng là ra id của office đấy
    //sau khi đã lấy ra được id thì mới được mở modal xác nhận xóa lên
    //khi người dùng chấp nhận xóa thì mới gọi api và nếu xóa thành công
    //thì cập nhập lại list và view lại.
    $(".xoa-office").click(function () {
        //khi click vào một nút nào đấy thì từ khóa this hiện tại chính là phần tử vừa click
        //.parents cho phép tìm kiếm các phần tử cha của phần tử hiện tại
        //.attr("name") name ở đây là key của một giá trị cần lấy.
        indexOffice = $(this).parents("tr").attr("data-index");
        $("#deleteModal").modal("show");
    })
}

async function xacNhanXoaOffice() {
    btnComfirmDelete.click(async function () {
        console.log("vao ham xoa");
        let id = listOffice[indexOffice - 0].officeCode;
        console.log("id can xoa: "+ id);
        //gọi api xóa office nếu thành công tức trả vể true;
        await deleteOffice(id).then(rs => {
            if (rs.message == "success"){
                console.log(rs)
                console.log("xoa thanh cong");
            }else {
                console.log(rs)
                console.log("xoa that bai");
            }
        }).catch(err => {
            console.log(err)
        })
        layTatCaOffice();
        $("#deleteModal").modal("hide");
    })
}

function suaOffice() {
    $(".sua-office").click(function () {
        elementOffice = listOffice[$(this).parents("tr").attr("data-index") - 0];
        inputOfficeCode.val(elementOffice.officeCode);
        inputCity.val(elementOffice.city);
        inputPhone.val(elementOffice.phone);
        inputAddress1.val(elementOffice.addressLine1);
        inputAddress2.val(elementOffice.addressLine2);
        inputSate.val(elementOffice.state);
        inputCountry.val(elementOffice.country);
        inputPostalCode.val(elementOffice.postalCode);
        inputTerritory.val(elementOffice.territory);
        $("#insertModal").modal("show");
    })
}

function checkData(selector, textError) {
    let val = $(selector).val();
    let check = false;
    if(val.length > 0) {
        check = true;
        hiddenError(selector);
    } else {
        viewError(selector, textError);
    }
    //trả về đối tượng có 2 thộc tính val và check thuộc tính val mang giá trị của val
    //thuộc tính check mang giá trị của check;
    return {val, check};
}

function luuOffice() {
    btnComfirmSave.click(async function () {
        // mỗi trường người dùng nhập vào đều phải kiểm tra bằng hàm checkData
        //hàng check data trả về 2 giá trị val và check
        // ở mỗi trả về ví dụ valTen sẽ mang giá trị của checkData cho ô tên
        let {val:valOfficeCode, check:checkOfficeode} = checkData(inputOfficeCode, "Định dạng chưa đúng");
        let {val:valCity, check:checkCity} = checkData(inputCity, "Định dạng chưa đúng");
        let {val:valPhone, check:checkPhone} = checkData(inputPhone, "Định dạng chưa đúng");
        let {val:valAddressLine1, check:checkAddressLine1} = checkData(inputAddress1, "Định dạng chưa đúng");
        let {val:valAddressLine2, check:checkAddressLine2} = checkData(inputAddress2, "Định dạng chưa đúng");
        let {val:valState, check:checkState} = checkData(inputSate, "Định dạng chưa đúng");
        let {val:valCountry, check:checkCountry} = checkData(inputCountry, "Định dạng chưa đúng");
        let {val:valPostalCode, check:checkPostalCode} = checkData(inputPostalCode, "Định dạng chưa đúng");
        let {val:valTerritory, check:checkTerritory} = checkData(inputTerritory, "Định dạng chưa đúng");

        //nếu tất cả chính xác định dạng thì gán lại các thuộc tính cho phần tử cần sửa
        if(checkOfficeode && checkCity && checkPhone && checkAddressLine1 && checkAddressLine2 && checkState && checkCountry && checkPostalCode && checkTerritory) {
            let checkAction = false; // true là sửa, false là thêm
            if(elementOffice) {
                checkAction = true;
            } else {
                elementOffice = {};
            }
            elementOffice.officeCode = valOfficeCode;
            elementOffice.city = valCity;
            elementOffice.phone = valPhone;
            elementOffice.addressLine1 = valAddressLine1;
            elementOffice.addressLine2 = valAddressLine2;
            elementOffice.state = valState;
            elementOffice.country = valCountry;
            elementOffice.postalCode = valPostalCode;
            elementOffice.territory = valTerritory;
            if(checkAction) {
                // sửa sản phẩm
                //Sau khi thay đổi các trường xong gọi đến api nếu api trả về truu thì
                //gán đối tượng api trả về vào listOffice ứng với index của nó lúc đầu.
                updatetOffice(elementOffice);
                console.log("update office");
                //sau khi save thành công thì phải đóng modal
            } else {
                // thêm sản phẩm
                // gọi api thêm office nếu thành công api sẽ trả về một đối tượng
                //vừa đươc thêm vào đã có id
                // nếu thành công push đối tượng mới vào mảng listOffice và
                // thực hiện hiện view lại danh sách.
                insertOffice(elementOffice);
                console.log("insert office")
            }
            //Sau khi thay đổi các trường xong gọi đến api nếu api trả về true thì
            //gán đối tượng api trả về vào listOffice ứng với index của nó lúc đầu.
            //sau đấy gọi lại view danh sách office
            await layTatCaOffice();
            //sau khi save thành công thì phải đóng modal
            $("#insertModal").modal("hide");
        }

    })
}

function themOffice() {
    btnAddOffice.click(function () {
        elementOffice = null;
        $("#insertModal").modal("show");
    })
}

function tienTrang(totalPage){
    btnNext.click(async function () {
        console.log("click next")
        currentPage++;
        if (currentPage > totalPage){
            currentPage = totalPage;
        }
        renderDataForPage(currentPage);
    })
}
function luiTrang(){
    btnPrev.click(async function () {
        console.log("click prev")
        currentPage--;
        if (currentPage <= 1){
            currentPage = 1;
        }
        renderDataForPage(currentPage);
    })
}

function renderListPage(totalPage){
    let view = '';
    view += `<li class="page-item"><a class="page-link" href="#">1</a></li>`;
    console.log(totalPage)
    for (let i = 2; i<= totalPage; i++){
        console.log("chay for")
        view += `<li class="page-item"><a class="page-link" href="#">${i}</a></li>`;
    }
    console.log(view);
    document.getElementById('number-page').innerHTML = view;
    changePage();
}

function changePage(){
    const currentPages = document.querySelectorAll('.number-page li');
    console.log(currentPages)
    for (let i =0;i<currentPages.length;i++){
        currentPages[i].addEventListener('click', ()=>{
            const value = i+1;
            currentPage = value;
            console.log("crp: "+currentPage);
            renderDataForPage(currentPage);
        })
    }
}

function renderDataForPage(currentPage){
    start = (currentPage-1) * perPage;
    end = currentPage * perPage;
    viewDanhSachOffice()
}