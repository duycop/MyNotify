
document.addEventListener("DOMContentLoaded", function() {
	var id=1;
	// Lấy tham chiếu đến nút
    var getNewsButton = document.getElementById('cmd_get_news');

    // Gắn sự kiện click cho nút
    getNewsButton.addEventListener('click', function() {
        get_json();
    });
    
    function get_json(){
    	// Truy cập URL và xử lý JSON khi nút được click
        fetch('api.php?id=' + id)
            .then(response => response.json())
            .then(data => {
                // Xử lý dữ liệu JSON ở đây
	            var s='Nhận được ok='+data.ok;
	            if(data.ok){
	            	id = data.id ; //cập nhật id mới
	            	s+=' id='+data.id;
	            	s+=' msg='+data.msg;
	            	
	            	//gui ve class MainActivity.java
	            	window.DuyCop.jsThongBao2(data.tieude,data.msg)
	            }
	            document.getElementById('ketqua').innerHTML=s
	            
	            console.log(data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                var s='Error fetching data:'+ error
                document.getElementById('ketqua').innerHTML=s
            });
	}
	setInterval(get_json, 5000); //timer auto call function get_json every 5 seconds
});
