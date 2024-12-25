<template>

  <div class="app-container">

    <el-row>
      <el-card>
        <div id="video-player">
          <video id="video" controls autoplay>
            <source :src="videoPath" type="video/mp4">
          </video>
        </div>
      </el-card>
      <el-card class="qu-content">
        <!--     试题内容 -->

        <p v-if="quData.type === '1'">单选题（10分）</p>
        <p v-else>简答题（10分）</p>
        <p>{{ (sort + 1) }}.{{ quData.title }}</p>
        <div v-if="quData.type === '1' && quData.tag === '2'">
          <el-radio-group v-model="radioValue">
            <el-radio v-for="item in quData.list" :label="item.id" :key="item.id">{{ item.sort }}.{{ item.content }}
            </el-radio>
          </el-radio-group>
        </div>

        <div v-if="quData.tag === '1' && quData.tag === '1'" style="clear: both">
          <el-radio-group v-model="radioValue">
            <el-radio v-for="item in quData.list" :label="item.id" :key="item.id">{{ item.sort }}
              <div>
                <el-image :src="item.content" style="height: 100px;width: 100px"/>
              </div>
            </el-radio>
          </el-radio-group>

        </div>

        <div v-if="quData.type === '2'">
          <el-input type="textarea"
                    :rows="5" v-model="contentValue" placeholder="请输入内容"/>
        </div>
      </el-card>

      <div style="margin-top: 20px">


        <el-button v-if="showNext" type="warning" icon="el-icon-right" @click="clickNext">
          下一题
        </el-button>
        <el-button v-if="showSend" type="primary" icon="el-icon-right" @click="doHandler">
          提交试卷
        </el-button>
      </div>

    </el-row>
  </div>

</template>

<script>
import { getLaboratoryProcedureById } from '@/api/laboratory'
import { sendLaboratory } from '@/api/laboratoryAppointment'
import { sendPaper } from '@/api/laboratoryInfo'
import Tinymce from '@/components/Tinymce'
import { Loading } from 'element-ui'
import { getToken } from '@/utils/auth'
export default {
  name: 'LaboratoryStart',
  components: { Tinymce }, // 注册组件

  data() {
    return {
      showNext: true,
      showSend: false,
      // 当前题目内容
      quData: {},
      // 所有试题
      list:[],
      //当前是第几试题
      sort: 0,
      // 单选选定值
      radioValue: '',
      // 简答题值
      contentValue: '',
      // 存放答案
      answerList:[],
      // 视频
      videoPath: '',
      appointmentId:''
    }
  },
  created() {

    const id = this.$route.params.id
    this.appointmentId = this.$route.params.appointmentId

    this.fetchData(id)

  },

  methods: {


    // 下一题
    clickNext() {

      // 初始化参数
      this.sort = this.sort + 1

      // 判断是否有下一题
      if (this.list.length === this.sort + 1) {
        this.showNext = false;
        this.showSend = true;
      }

      // 存储答案
      this.answerList.push({
        infoId: this.quData.infoId,
        radioValue: this.radioValue,
        contentValue: this.contentValue
      })
      this.radioValue = '';
      this.contentValue = '';
      // 切换试题
      this.getCutQuestion()

    },


    // 实验步骤详情
    fetchData(id) {
      getLaboratoryProcedureById(id).then(response => {
        this.videoPath = response.data.row.videoPath;
        document.querySelector('video').load();
        this.list = response.data.row.list;
        this.getCutQuestion()
      })
    },

    getCutQuestion() {
      this.quData = this.list[this.sort]
    },



    // 提交试卷
    doHandler() {
      // 打开
      const loading = Loading.service({
        text: '提交中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      // 存储答案
      this.answerList.push({
        infoId: this.quData.infoId,
        radioValue: this.radioValue,
        contentValue: this.contentValue
      })
      sendPaper(getToken(),this.appointmentId, this.answerList)
        .then(response => { // 请求成功
          loading.close()
          this.$message({
            message: '试题提交成功,本次考试结束！',
            type: 'success'
          })
          this.answerList = [];
          this.$router.push({ name: 'myProcedure' })
        })
    },

  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
#video-player {
  text-align: center;
  width: 100%;
  height: 490px;
  background-color: #000;
  position: relative;
}

#video-player video {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.el-radio + .el-radio {
  margin-left: 0;
}


.qu-content div {
  line-height: 30px;
}



.card-title {
  background: #eee;
  line-height: 35px;
  text-align: center;
  font-size: 14px;
}

.card-line {
  padding-left: 10px
}

.card-line span {
  cursor: pointer;
  margin: 2px;
}

.el-radio {
  padding: 9px 20px 9px 10px;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  margin-bottom: 10px;
}

.is-checked {
  border: #409eff 1px solid;
}


</style>

