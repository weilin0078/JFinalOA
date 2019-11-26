/**
 * Date日期类封装
 */
var dateUtil = function() {
	/***
	 * 比较日期
	 * @param startDate
	 * @param endDate
	 * @returns {boolean}
	 */
	var compareDate = function(startDate, endDate) {
		if (startDate&&endDate) {
			startDate = startDate + " 00:00:00";
			endDate = endDate + " 00:00:00";
			var allStartDate = new Date(startDate.replace(/-/g,"/") );
			var allEndDate = new Date(endDate.replace(/-/g,"/") );
			if (allStartDate.getTime() >= allEndDate.getTime()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	/****
	 * 比较时间
 	 * @param startDate
	 * @param endDate
	 * @returns {boolean}
	 */
	var compareTime = function(startDate, endDate) {
		if (startDate&&endDate) {
			var allStartDate = new Date(startDate.replace(/-/g,"/") );
			var allEndDate = new Date(endDate.replace(/-/g,"/") );
			if (allStartDate.getTime() >= allEndDate.getTime()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/***
	 * 比较时间和日期
 	 * @param startDate
	 * @param endDate
	 * @returns {boolean}
	 */
	var compareCalendar = function(startDate, endDate) {
		if (startDate.indexOf(" ") != -1 && endDate.indexOf(" ") != -1 ) {
			//包含时间，日期
			return compareTime(startDate, endDate);
		} else {
			//不包含时间，只包含日期
			return compareDate(startDate, endDate);
		}
	}

	/***
	 * 计算时间差
	 * @param start
	 * @param end
	 * @returns {number}   数字
	 *   取出数字后，方便计算
	 *   var day = parseInt(total / (24*60*60));//计算整数天数
	 *   var afterDay = total - day*24*60*60;//取得算出天数后剩余的秒数
	 *	 var hour = parseInt(afterDay/(60*60));//计算整数小时数
	 *	 var afterHour = total - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
	 * 	 var min = parseInt(afterHour/60);//计算整数分
	 *	 var afterMin = total - day*24*60*60 - hour*60*60 - min*60;//取得算出分后剩余的秒数
	 */
	var timeSubtract = function(start,end){
		if (start&&end) {
			var startDate = new Date(start.replace(/-/g,"/") );
			var endDate = new Date(end.replace(/-/g,"/") );
			var total = (endDate - startDate)/1000;
			return total
		} else {
			return 0;
		}
	}
	return {
		compareDate : compareDate,
		compareTime : compareTime,
		compareCalendar : compareCalendar,
		timeSubtract : timeSubtract

	};

}();





    




