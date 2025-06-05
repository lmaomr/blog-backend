// 文件操作相关功能
document.addEventListener('DOMContentLoaded', function() {
    // 上传按钮点击事件
    const uploadBtn = document.querySelector('.btn:first-child');
    uploadBtn.addEventListener('click', function() {
        // 创建文件输入元素
        const input = document.createElement('input');
        input.type = 'file';
        input.multiple = true;
        input.click();

        input.onchange = function() {
            // 这里添加文件上传逻辑
            console.log('选择文件:', input.files);
        };
    });

    // 新建文件夹按钮点击事件
    const newFolderBtn = document.querySelector('.btn:nth-child(2)');
    newFolderBtn.addEventListener('click', function() {
        const folderName = prompt('请输入文件夹名称：');
        if (folderName) {
            // 这里添加创建文件夹逻辑
            console.log('创建文件夹:', folderName);
        }
    });

    // 文件操作图标点击事件
    const actionIcons = document.querySelectorAll('.col-actions i');
    actionIcons.forEach(icon => {
        icon.addEventListener('click', function(e) {
            e.stopPropagation();
            const action = this.className;
            const fileItem = this.closest('.file-item');
            const fileName = fileItem.querySelector('.col-name span').textContent;

            if (action.includes('share-alt')) {
                // 分享文件
                console.log('分享文件:', fileName);
            } else if (action.includes('download')) {
                // 下载文件
                console.log('下载文件:', fileName);
            } else if (action.includes('trash')) {
                // 删除文件
                if (confirm(`确定要删除 ${fileName} 吗？`)) {
                    console.log('删除文件:', fileName);
                }
            }
        });
    });

    // 搜索框功能
    const searchInput = document.querySelector('.search-box input');
    searchInput.addEventListener('input', function(e) {
        const searchText = e.target.value.toLowerCase();
        const fileItems = document.querySelectorAll('.file-item');
        
        fileItems.forEach(item => {
            const fileName = item.querySelector('.col-name span').textContent.toLowerCase();
            if (fileName.includes(searchText)) {
                item.style.display = '';
            } else {
                item.style.display = 'none';
            }
        });
    });

    // 文件项点击事件（进入文件夹）
    const fileItems = document.querySelectorAll('.file-item');
    fileItems.forEach(item => {
        item.addEventListener('click', function() {
            const isFolder = this.querySelector('.fa-folder') !== null;
            if (isFolder) {
                const folderName = this.querySelector('.col-name span').textContent;
                // 这里添加进入文件夹逻辑
                console.log('进入文件夹:', folderName);
            }
        });
    });
}); 