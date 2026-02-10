// Driver Dashboard JavaScript - Simple Imperative Style

document.addEventListener('DOMContentLoaded', function() {
	var appRoot = document.getElementById('appRoot');
	            var ctx = (appRoot && appRoot.getAttribute('data-ctx')) ? appRoot.getAttribute('data-ctx') : '${pageContext.request.contextPath}';

	            var notifBell = document.getElementById('notificationBell');
	            var notifDropdown = document.getElementById('notificationDropdown');
	            var notifList = document.getElementById('notificationDropdownList');
	            var profileBtn = document.getElementById('profileButton');
	            var profileDropdown = document.getElementById('profileDropdown');

	            function hideAll() {
	                if (notifDropdown) notifDropdown.style.display = 'none';
	                if (profileDropdown) profileDropdown.style.display = 'none';
	            }

	            function parseMetadata(md) {
	                if (!md) return {};
	                if (typeof md === 'object') return md;
	                try { return JSON.parse(md); } catch (e) { return {}; }
	            }

	            function absoluteUrl(url) {
	                if (!url) return '#';
	                if (url.indexOf('http://') === 0 || url.indexOf('https://') === 0) return url;
	                if (ctx && url.indexOf(ctx) === 0) return url;
	                return (url.charAt(0) === '/') ? (ctx + url) : (ctx + '/' + url);
	            }

	            function renderNotifications(items) {
	                notifList.innerHTML = '';
	                if (!items || !items.length) {
	                    notifList.innerHTML = '<div class="notification-empty">No new notifications</div>';
	                    return;
	                }
	                items.forEach(function (n) {
	                    var meta = parseMetadata(n.metadata);
	                    var row = document.createElement('div');
	                    row.className = 'notification-item';

	                    var msg = (n.message || '');
	                    var created = (n.createdAt || '');
	                    var body =
	                        '<div class="notification-message">' + msg + '</div>' +
	                        '<div class="notification-time">' + created + '</div>' +
	                        (meta.cta ? '<div class="notification-cta">' + meta.cta + '</div>' : '');

	                    if (meta.url) {
	                        var a = document.createElement('a');
	                        a.href = absoluteUrl(meta.url);
	                        a.className = 'notification-link';
	                        a.innerHTML = body;
	                        row.appendChild(a);
	                    } else {
	                        row.innerHTML = body;
	                    }

	                    notifList.appendChild(row);
	                });
	            }

	            function loadUnread() {
	                return fetch(ctx + '/notifications/unread?limit=10', { credentials: 'same-origin' })
	                    .then(function (r) {
	                        if (!r.ok) throw new Error('Failed to load notifications');
	                        return r.json();
	                    })
	                    .then(function (data) {
	                        renderNotifications(data.items);
	                    });
	            }

	            function markAllRead() {
	                return fetch(ctx + '/notifications/mark-read', {
	                    method: 'POST',
	                    credentials: 'same-origin'
	                }).then(function (r) {
	                    if (!(r.ok || r.status === 204)) {
	                        console.warn('Mark-read failed', r.status);
	                    }
	                    var badge = notifBell.querySelector('.notification-badge');
	                    if (badge) badge.remove();
	                });
	            }

	            if (notifBell) {
	                notifBell.addEventListener('click', function (e) {
	                    e.stopPropagation();
	                    var open = (notifDropdown.style.display === 'block');
	                    hideAll();
	                    if (!open) {
	                        notifDropdown.style.display = 'block';
	                        notifBell.setAttribute('aria-expanded', 'true');
	                        loadUnread().then(markAllRead).catch(function (err) { console.error(err); });
	                    } else {
	                        notifBell.setAttribute('aria-expanded', 'false');
	                    }
	                });
	            }

	            if (profileBtn) {
	                profileBtn.addEventListener('click', function (e) {
	                    e.stopPropagation();
	                    var open = (profileDropdown.style.display === 'block');
	                    hideAll();
	                    profileDropdown.style.display = open ? 'none' : 'block';
	                });
	            }

	            document.addEventListener('click', hideAll);
	        })();