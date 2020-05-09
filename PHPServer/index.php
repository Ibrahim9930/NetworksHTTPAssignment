<?php
session_start();
$myObj = new \stdClass();
if(isset($_REQUEST['id'])&&isset($_REQUEST['password'])){
    if($_REQUEST['id']&&$_REQUEST['password']){
        @$con = mysqli_connect('127.0.0.1','root','');
        mysqli_select_db($con,'Inventory_system');
        $id = mysqli_real_escape_string($con,$_REQUEST['id']);
        $password = mysqli_real_escape_string($con,$_REQUEST['password']);

        $sql= " SELECT * from employees where id = '$id' and password = '$password'";
        $result = mysqli_query($con,$sql);
        $count = mysqli_num_rows($result);

        if($count < 1){
            $myObj -> success = false;
            echo json_encode($myObj);
        }
        else{
            $row = mysqli_fetch_assoc($result);
            $last_access = $row["last_access"];
            $_SESSION['id'] = $id;
            $myObj -> success = true;
            $myObj -> lastLogIn = (int)$last_access;
            echo json_encode($myObj);

            $milliseconds = round(microtime(true) * 1000);
            $sql = "UPDATE employees SET last_access = '$milliseconds' WHERE id = '$id'";
            $result = mysqli_query($con,$sql);
        }
    }
    else{
        $myObj -> success = false;
        echo json_encode($myObj);
    }
}
else{
    $myObj -> success = false;
    echo json_encode($myObj);
}

?>