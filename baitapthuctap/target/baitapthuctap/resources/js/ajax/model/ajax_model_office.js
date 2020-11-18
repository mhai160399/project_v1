var urlOffice = "office/";

function officeFindAll(){
    return ajaxGet(`${urlOffice}find-all`);
}

function officeFindById(id){
    return ajaxGet(`${urlOffice}find-by-id?id=${id}`)
}

function searchOffice(text){
    return ajaxGet(`${urlOffice}search?text=${text}`)
}

function insertOffice(data){
    return ajaxPost(`${urlOffice}`,data)
}

function updatetOffice(data){
    return ajaxPut(`${urlOffice}`,data)
}

function deleteOffice(id){
    return ajaxDelete(`${urlOffice}?id=${id}`)
    // return ajaxDelete(`${urlOffice}delete-office?id=${id}`)
}