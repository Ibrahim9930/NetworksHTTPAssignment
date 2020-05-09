<?php
session_start(); 
if(!true){
    echo "error1";
}
else{
    if(isset($_REQUEST['productID'])){
        @$con = mysqli_connect('127.0.0.1','root','');
        mysqli_select_db($con,'Inventory_system');
        $id = $_REQUEST['productID'];
        $added_amount = $_REQUEST['amount'];

        $sql= " SELECT * from products where id = '$id'";
        $result = mysqli_query($con,$sql);
        $count = mysqli_num_rows($result);
        if($count < 1){
            echo '{"success":false}';
        }
        else{
            $row = mysqli_fetch_assoc($result);
            $amount = $row["amount"];
            $new_amount = $amount + $added_amount;
            $sql = "UPDATE products SET amount = '$new_amount' WHERE id = '$id'";
            $result = mysqli_query($con,$sql);
            echo '{"success":true}';
        }

    }
    else{
        echo "error2";
    }
    
}

?>