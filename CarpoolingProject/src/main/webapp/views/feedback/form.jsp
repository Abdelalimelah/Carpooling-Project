<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Feedback</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 500px;
            margin: 40px auto;
            background: #f9f9f9;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        h2 { text-align: center; color: #333; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        textarea {
            width: 100%;
            height: 100px;
            padding: 8px;
            box-sizing: border-box;
            border-radius: 5px;
            border: 1px solid #ccc;
            resize: none;
        }
        .stars {
            font-size: 30px;
            color: #ccc;
            display: flex;
            gap: 8px;
        }
        .star {
            cursor: pointer;
            transition: color 0.2s;
        }
        .star.selected {
            color: #FFD700;
        }
        .btn {
            background: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            font-size: 16px;
        }
        .btn:hover { background: #45a049; }
        .error {
            color: red;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
    <h2>Rate Your Experience</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error"><%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/feedback" method="post">
        <input type="hidden" name="tripId" value="<%= request.getAttribute("tripId") %>">
        <input type="hidden" name="targetId" value="<%= request.getAttribute("targetId") %>">
        <input type="hidden" name="feedbackType" value="<%= request.getAttribute("feedbackType") %>">
        <input type="hidden" id="rating" name="rating" required>

        <div class="form-group">
            <label>Rating:</label>
            <div class="stars" id="starContainer">
                <span class="star" data-value="1">★</span>
                <span class="star" data-value="2">★</span>
                <span class="star" data-value="3">★</span>
                <span class="star" data-value="4">★</span>
                <span class="star" data-value="5">★</span>
            </div>
        </div>

        <div class="form-group">
            <label>Comment (optional):</label>
            <textarea name="comment" placeholder="Share your thoughts..."></textarea>
        </div>

        <button type="submit" class="btn">Submit Feedback</button>
    </form>

    <script>
        const stars = document.querySelectorAll('.star');
        const ratingInput = document.getElementById('rating');

        stars.forEach(star => {
            star.addEventListener('click', () => {
                const rating = parseInt(star.getAttribute('data-value'));
                ratingInput.value = rating;

                stars.forEach(s => {
                    s.classList.toggle('selected', parseInt(s.getAttribute('data-value')) <= rating);
                });
            });
        });
    </script>
</body>
</html>
