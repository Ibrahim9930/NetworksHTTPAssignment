<?php
session_start(); 
if(!true){
    echo "error1";
}
else{
    if(isset($_REQUEST['newPassword'])&&isset($_REQUEST['id'])){
        @$con = mysqli_connect('127.0.0.1','root','');
        mysqli_select_db($con,'Inventory_system');
        $id = $_REQUEST['id'];
        $new_password = $_REQUEST['newPassword'];
        $sql = "UPDATE employees SET password = '$new_password' WHERE id = '$id'";
        $result = mysqli_query($con,$sql);
        echo '{"success":true}';
    }
    else{
        echo '{"success":false}';
    }
    
}

?>