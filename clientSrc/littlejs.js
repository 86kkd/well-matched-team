// let gear = 'P';

// function shiftUp() {
//   if (gear === 'P') {
//     gear = 'R';
//   } else if (gear === 'R') {
//     gear = 'N';
//   } else if (gear === 'N') {
//     gear = 'O';
//   } else if (gear === 'O') {
//     gear = 'S'
//   } else if (gear === 'S') {
//     gear = 'L'
//   }
//   updateGearInfo();
// }

// function shiftDown() {
//   if (gear === 'L') {
//     gear = 'S';
//   } else if (gear === 'S') {
//     gear = 'O';
//   } else if (gear === 'O') {
//     gear = 'N';
//   } else if (gear === 'N') {
//     gear = 'R';
//   } else if (gear === 'R') {
//     gear = 'P';
//   }
//   updateGearInfo();
// }

// function updateGearInfo() {
//   const gearInfo = document.querySelector('.gear-info span');
//   gearInfo.textContent = `挡位：${gear}`;
// }
// let state = '开';
// function change() {
//   if (state === '开') {
//     state = '关'
//   }
//   else if (state === '关') {
//     state = '开'
//   }
//   updateGearInfo1()
// }
// function updateGearInfo1() {
//   const stateInfo = document.querySelector('.safe1 span');
//   stateInfo.textContent = `安全气囊状态：${state}`;
// }
// let state2 = '开';
// function change1() {
//   if (state2 === '开') {
//     state2 = '关'
//   }
//   else if (state2 === '关') {
//     state2 = '开'
//   }
//   updateGearInfo2()
// }
// function updateGearInfo2() {
//   const state2Info = document.querySelector('.safe2 span');
//   state2Info.textContent = `安全带：${state2}`;
// }


//=======================================
var img = document.getElementById("video");
var url = "http://www.016imcn.love:4567/getLatestImage-test"; // 这里替换成你自己的接口地址
var intervalId = null; // 用于存储定时器的ID
let fileInput = document.getElementById('upload-form');
var fileName = fileInput.value;
console.log(fileName)
function refreshImage() {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        img.src = URL.createObjectURL(xhr.response);
        document.getElementById('status').innerHTML = '图片下载成功！';
      } else {
        document.getElementById('status').innerHTML = '图片下载失败！';
      }
    }
  };
  xhr.open('get', url, true);
  xhr.responseType = 'blob';
  xhr.send();
}
function startImageRefresh() {
  refreshImage(); // 立即执行一次刷新图片
  intervalId = setInterval(refreshImage, 1000); // 每隔0.1秒刷新一次
}
window.onload = function () {
  var uploadForm = document.getElementById('upload-form');
  uploadForm.addEventListener('submit', function (event) {
    event.preventDefault(); // 阻止默认提交行为

    var formData = new FormData(uploadForm);

    var xhr1 = new XMLHttpRequest();
    xhr1.open('post', uploadForm.action, true);
    xhr1.onreadystatechange = function () {
      if (xhr1.readyState === 4) {
        if (xhr1.status === 200) {
          console.log(xhr1.responseText); // 服务器返回的响应内容
          document.getElementById('status1').innerHTML = '视频上传成功！';
        } else {
          document.getElementById('status1').innerHTML = '视频上传失败！';
        }

        if (intervalId === null) {
          startImageRefresh(); // 仅在上传成功后启动定时器
        }
      }
    };
    xhr1.send(formData);
  });
};
console.log(fileName)
//=========================================
const buttons = document.querySelectorAll('.upbutton');

// 循环为每一个按钮绑定鼠标移动事件
buttons.forEach((button) => {
  button.addEventListener('mousemove', (e) => {
    // 获取鼠标的x、y坐标
    const x = e.offsetX;
    const y = e.offsetY;
    // 将x、y坐标赋值给CSS中的自定义变量--x、--y
    button.style.setProperty('--x', `${x}px`);
    button.style.setProperty('--y', `${y}px`);
  })
})


//=========================================
//视频播放接口
// var video = document.getElementById("video");
// var url = "http://www.016imcn.love:4567/downloadVideo"; // 替换为您自己的接口地址

// function loadVideo() {
//   var xhr = new XMLHttpRequest();
//   xhr.onreadystatechange = function () {
//     if (xhr.readyState === 4 && xhr.status === 200) {

//       var blob = xhr.response;
//       var videoURL = URL.createObjectURL(blob);
//       document.getElementById('status').innerHTML = '视频下载成功！';
//       // document.getElementById('video').hidden = false;
//       // 将视频的URL设置给<video>元素的src属性
//       video.src = videoURL;

//       // 当视频播放结束后，释放资源，并重新加载新的视频
//       video.onended = function () {
//         URL.revokeObjectURL(videoURL);
//         loadVideo();
//       };
//     }
//   };

//   xhr.open('GET', url, true);
//   xhr.responseType = 'blob';
//   xhr.send();
// }

// // 页面加载完成后，开始加载第一次视频
// window.onload = function () {
//   var uploadForm = document.getElementById('upload-form');
//   uploadForm.addEventListener('submit', function (event) {
//     event.preventDefault(); // 阻止默认提交行为

//     var formData = new FormData(uploadForm);

//     var xhr = new XMLHttpRequest();
//     xhr.open('POST', uploadForm.action, true);
//     xhr.onreadystatechange = function () {
//       if (xhr.readyState === 4 && xhr.status === 200) {
//         console.log(xhr.responseText); // 服务器返回的响应内容
//         document.getElementById('status').innerHTML = '视频上传成功！';
//         loadVideo();
//       }
//     };
//     xhr.send(formData);
//   });
// };
//=============================================
// 要操作的画布
const cvs = document.querySelector('canvas')
// 画布上下文
const ctx = cvs.getContext('2d')

function init() {
  cvs.width = window.innerWidth * devicePixelRatio
  cvs.height = window.innerHeight * devicePixelRatio
}
init()
// 根据dpr计算字体大小(devicePixelRatio 设备像素比)
const fonSize = 20 * devicePixelRatio
ctx.font = `${fonSize}px "Consolas"`
const columnCount = Math.floor(cvs.width / fonSize)
const charIndex = new Array(columnCount).fill(0)

function draw() {
  ctx.fillStyle = 'rgba(0,0,0,0.1)'
  ctx.fillRect(0, 0, cvs.width, cvs.height)
  ctx.fillStyle = '#6be445'
  ctx.textBaseline = 'top'
  for (let i = 0; i < columnCount; i++) {
    const text = getRandomChar()
    const x = i * fonSize
    const y = charIndex[i] * fonSize
    ctx.fillText(text, x, y)
    if (y > cvs.height && Math.random() > 0.99) {
      charIndex[i] = 0
    } else {
      charIndex[i]++
    }
  }

}
draw()
setInterval(draw, 50)
function getRandomChar() {
  const str = '0123456789abcdefghijklmnopqrstuvwxyz'
  return str[Math.floor(Math.random() * str.length)]
}