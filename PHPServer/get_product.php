<?php
session_start(); 
$myObj = new \stdClass();
if(!true){
    echo "error1";
}
else{
    if(isset($_REQUEST['productID'])){
        @$con = mysqli_connect('127.0.0.1','root','');
        mysqli_select_db($con,'Inventory_system');
        $id = $_REQUEST['productID'];

        $sql= " SELECT * from products where id = '$id'";
        $result = mysqli_query($con,$sql);
        $count = mysqli_num_rows($result);
        if($count < 1){
            echo'{"success":false}';
        }
        else{
            echo '{"success":true,"product":';
            $row = mysqli_fetch_assoc($result);
            $myObj -> id = $row["id"];
            $myObj -> name = $row["name"];
            $myObj -> amount = (int)$row["amount"];
            $myObj -> pricePerItem = (double)$row["price"];
            echo json_encode($myObj);
            echo "}";
        }

    }
    else{
        echo "error2";
    }
    
}

?>