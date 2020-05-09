<?php
session_start(); 
$myObj = new \stdClass();
if(!true){
    echo "error1";
}
else{

    @$con = mysqli_connect('127.0.0.1','root','');
    mysqli_select_db($con,'Inventory_system');

    $sql= " SELECT * from products";
    $result = mysqli_query($con,$sql);
    $count = mysqli_num_rows($result);
    $myObj = new \stdClass();
    echo "[";
    for($i = 0; $i<$count; $i++){
        $row = mysqli_fetch_assoc($result);
        $myObj -> id = $row["id"];
        $myObj -> name = $row["name"];
        $myObj -> pricePerItem = (double)$row["price"];
        $myObj -> amount = (int)$row["amount"];
        echo json_encode($myObj);
        if($i < $count - 1) echo ",";
    }
    echo "]";


    
}

?>