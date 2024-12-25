<template>
  <div class="dashboard-container">
    <el-card class="TopCard" style="height:60px">
      <!-- 小喇叭 -->
      <i class="el-icon-chat-dot-round" style="color:#606266"/>
      <span class="tips">公告：</span>
      <span class="notice">{{ noticeContent }}</span>
    </el-card>
    <el-row class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="user" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              学生量
            </div>
            <count-to :start-val="0" :end-val="indexData.studentNumber" :duration="1000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="user" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              教师量
            </div>
            <count-to :start-val="0" :end-val="indexData.teacherNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              预约实验
            </div>
            <count-to :start-val="0" :end-val="indexData.laboratoryNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              完成实验
            </div>
            <count-to :start-val="0" :end-val="indexData.accomplishNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-card class="box-card" style="margin: 10px">
        <div slot="header" class="clearfix">
          <span>预约变化统计图</span>
          <el-radio-group v-model="type"  style="float: right;"  @change="init">
            <el-radio-button label="1">按月</el-radio-button>
            <el-radio-button label="2">按日</el-radio-button>
          </el-radio-group>
        </div>
        <!-- 统计图容器 -->
        <div id="main" style="width: 100%;height: 270px;" />
      </el-card>
    </el-row>
    <el-row >
      <el-col :span="12">
        <el-card class="box-card" style="margin: 10px">
          <div slot="header" class="clearfix">
            <span>实验资源科目占比</span>
          </div>
          <!-- 统计图容器 -->
          <div id="main1" style="width: 100%;height: 350px;" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" style="margin: 10px">
          <div slot="header" class="clearfix">
            <span>实验预约科目占比</span>
          </div>
          <!-- 统计图容器 -->
          <div id="main2" style="width: 100%;height: 350px;" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import CountTo from 'vue-count-to'
import { getHome } from '@/api/user'
import { getZzt } from '@/api/laboratory'
import { getNoticeAll } from '@/api/notice'
export default {
  name: 'Dashboard',
  components: {
    CountTo
  },
  computed: {

    ...mapGetters([
      'name',
      'roles'
    ]),
  },
  created() {
    this.init();
    this.getNoticeInit();
  },
  methods: {
    init(){
      getHome().then(res=>{
        this.indexData = res.data.data
      })
      this.getZzt();
    },
    getZzt(){
      getZzt(this.type).then(res=>{
        var echarts = require('echarts')
        // 初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'))
        // 配置参数
        var option = {
          xAxis: {
            type: 'category',
            axisLine: {
              lineStyle: {
                color: '#D7DDE4'
              }
            },
            axisTick: {
              alignWithLabel: true,
              lineStyle: {
                color: '#D7DDE4'
              }
            },
            axisLabel: {
              textStyle: {
                color: '#7F8B9C'
              }
            },
            splitLine: {
              show: false,
              lineStyle: {
                color: '#F5F7F9'
              }
            },
            data:  res.data.data.day,
          },
          yAxis: {
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#7F8B9C'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F5F7F9'
              }
            },
            type: 'value'
          },
          series: [{
            name: '预约量',
            data:  res.data.data.number,
            type: 'bar',
            tooltip: true,
            smooth: true,
            symbol: 'none',
            itemStyle: {
              normal: {
                barBorderRadius: [3, 3, 0, 0],
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: '#00CACA'
                  },
                  {
                    offset: 0.5,
                    color: '#00CACA'
                  },
                  {
                    offset: 1,
                    color: '#00CACA'
                  }
                ])
              }
            }
          }],
          grid: {
            left: 25,
            right: 25,
            bottom: 5,
            top: 30,
            containLabel: true
          },
          tooltip: {
            trigger: 'axis'
          }
        }
        myChart.setOption(option)


        // 初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('main1'))
        // 配置参数
        var option1 = {
          title: {
            text: '实验资源科目占比',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '数量',
              type: 'pie',
              radius: '50%',
              data: res.data.data.laboratoryList,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  color:function(params) {
                    //自定义颜色
                    var colorList = [
                      '#2f9bff',
                      '#00FFFF',
                      '#00FF00',
                      '#FFFF00',
                      '#FF8C00',
                      '#FF0000',
                      '#FE8463',
                      '#ff4d4f',
                      '#77e19d',
                      '#a3d3ff'
                    ];
                    return colorList[params.dataIndex]
                  }
                }
              }
            }
          ]
        }
        myChart1.setOption(option1)

        // 初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('main2'))
        // 配置参数
        var option2 = {
          title: {
            text: '实验预约科目占比',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '数量',
              type: 'pie',
              radius: '50%',
              data: res.data.data.appointmentList,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  color:function(params) {
                    //自定义颜色
                    var colorList = [
                      '#2f9bff',
                      '#00FFFF',
                      '#00FF00',
                      '#FFFF00',
                      '#FF8C00',
                      '#FF0000',
                      '#FE8463',
                      '#ff4d4f',
                      '#77e19d',
                      '#a3d3ff'
                    ];
                    return colorList[params.dataIndex]
                  }
                }
              }
            }
          ]
        }
        myChart2.setOption(option2)
      })
    },
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    },
    getNoticeInit(){
      getNoticeAll().then(res=>{
        this.notice = res.data.list;
        this.getNotice();
      })
    },
    getNotice(){
      if ((this.noticeIndex + 1) === this.notice.length) {
        this.noticeIndex = 0
      } else {
        this.noticeIndex += 1;
      }
      this.noticeContent = this.notice[this.noticeIndex].content
      setTimeout(() => {
        this.getNotice()
      }, 2000)
    },
  },
  data() {
    return {
      notice: '',
      noticeIndex: 0,
      noticeContent: '',
      indexData: {
        studentNumber: 0,
        teacherNumber: 0,
        laboratoryNumber: 0,
        accomplishNumber: 0
      },
      type: '1',
      activeNames: ['1', '2', '3', '4']
    };
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
