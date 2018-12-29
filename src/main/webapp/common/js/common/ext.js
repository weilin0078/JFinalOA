/**
 * 扩展Array方法, 去除数组中空白数据
 */
Array.prototype.notempty = function() {
    var arr = [];
    this.map(function(val, index) {
        //过滤规则为，不为空串、不为null、不为undefined，也可自行修改
        if (val !== "" && val != undefined) {
            arr.push(val);
        }
    });
    return arr;
}

/*****
 * 扩展array方法，去除数组中的重复数据
 */
function array_unique(arr){
	  var hash=[];
	  for (var i = 0; i < arr.length; i++) {
	    for (var j = i+1; j < arr.length; j++) {
	      if(arr[i]===arr[j]){
	        ++i;
	      }
	    }
	      hash.push(arr[i]);
	  }
	  return hash;
}

/**
 * 使用循环的方式判断一个元素是否存在于一个数组中
 * @param {Object} arr 数组
 * @param {Object} value 元素值
 */
function array_isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}