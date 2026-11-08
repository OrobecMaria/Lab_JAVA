<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>Административно-территориальное деление</title>
</head>
<body>

<div class="container-fluid">
    <jsp:include page="/views/header.jsp"/>

    <div class="container">
        <br><br><br>
        <div class="list-group text-center py-3 px-3 shadow-sm">
            <h2>Разделы системы</h2>
            <ul class="list-group list-group-flush">
                <li class="list-group-item list-group-item-primary">
                    <a href="#" class="text-decoration-none text-dark">Области</a>
                </li>
                <li class="list-group-item list-group-item-info">
                    <a href="#" class="text-decoration-none text-dark">Районы</a>
                </li>
            </ul>
        </div>
        <br><br>

        <!-- Тестовая кнопка Bootstrap (по методичке) -->
        <button type="button" class="btn btn-primary" data-bs-toggle="popover" title="Сообщение" data-bs-content="Ура, Bootstrap 5 работает">
            Поднеси ко мне курсор (кликни)
        </button>
    </div>

    <jsp:include page="/views/footer.jsp"/>
</div>

<!-- jQuery -->
<script src="js/jquery-3.6.4.js"></script>
<!-- Bootstrap JS Bundle (включает Popper) -->
<script defer src="js/bootstrap.bundle.min.js"></script>

<script>
    // Инициализация popover для Bootstrap 5
    $(function () {
        $('[data-bs-toggle="popover"]').popover({
            trigger: 'hover focus'
        })
    })
</script>

</body>
</html>