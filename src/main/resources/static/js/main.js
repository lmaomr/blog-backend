// 文件操作相关功能
document.addEventListener('DOMContentLoaded', function() {
    // 视图切换
    const viewBtns = document.querySelectorAll('.view-btn');
    const fileList = document.querySelector('.file-list');
    
    viewBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            viewBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            
            if (btn.dataset.view === 'grid') {
                fileList.classList.remove('list-view');
            } else {
                fileList.classList.add('list-view');
            }
        });
    });

    // 侧边栏切换
    const sidebarToggle = document.querySelector('.sidebar-toggle');
    const sidebar = document.querySelector('.sidebar');
    const mainContent = document.querySelector('.main-content');

    sidebarToggle.addEventListener('click', () => {
        sidebar.classList.toggle('active');
        if (sidebar.classList.contains('active')) {
            mainContent.style.marginLeft = '220px';
            sidebarToggle.style.left = '236px';
        } else {
            mainContent.style.marginLeft = '0';
            sidebarToggle.style.left = '16px';
        }
    });

    // 文件操作
    const fileItems = document.querySelectorAll('.file-item');
    
    fileItems.forEach(item => {
        // 点击文件项
        item.addEventListener('click', (e) => {
            if (!e.target.closest('.file-actions')) {
                console.log('打开文件:', item.querySelector('.file-name').textContent);
            }
        });

        // 文件操作按钮
        const actions = item.querySelectorAll('.file-actions i');
        actions.forEach(action => {
            action.addEventListener('click', (e) => {
                e.stopPropagation();
                const actionType = action.dataset.action;
                const fileName = item.querySelector('.file-name').textContent;
                
                switch(actionType) {
                    case 'download':
                        console.log('下载文件:', fileName);
                        break;
                    case 'share':
                        console.log('分享文件:', fileName);
                        break;
                    case 'delete':
                        console.log('删除文件:', fileName);
                        break;
                }
            });
        });
    });

    // 上传按钮
    const uploadBtn = document.querySelector('.btn-primary');
    uploadBtn.addEventListener('click', () => {
        console.log('打开上传对话框');
    });

    // 搜索功能
    const searchInput = document.querySelector('.search-box input');
    searchInput.addEventListener('input', (e) => {
        console.log('搜索:', e.target.value);
    });

    // 导航菜单
    const navItems = document.querySelectorAll('nav ul li');
    navItems.forEach(item => {
        item.addEventListener('click', () => {
            navItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');
        });
    });
}); 