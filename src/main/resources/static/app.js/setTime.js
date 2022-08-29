window.addEventListener('load', () => {
    let skeleton = document.querySelectorAll('.skeleton');
    
    setTimeout(() => {
        for(let i= 0; i < skeleton.length; i++) {
            skeleton[i].classList.remove('skeleton');
        }
        
    }, 3000)
})