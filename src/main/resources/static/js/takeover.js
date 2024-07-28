$(document).ready(function() {
    $('#login').on('click', function(event) {
        console.log('login');
        event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

        var username = $('#username').val();
        var password = $('#password').val();

        $.ajax({
            url: '/takeover/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                username: username,
                password: password
            }),
            success: function(response) {
                console.log('로그인 성공:', response);
                if(response.k) {
                    $.cookie('K', response.k);
                    console.log($.cookie('K'));
                }
                // 로그인 성공 시 수행할 작업
                // 예: 메인 페이지로 리다이렉션
                // window.location.href = '/main';
            },
            error: function(xhr, status, error) {
                console.error('로그인 실패:', error);
                // 로그인 실패 시 수행할 작업
                // 예: 에러 메시지 표시
                $('#errorMessage').text('로그인에 실패했습니다. 다시 시도해주세요.');
            }
        });
    });
});