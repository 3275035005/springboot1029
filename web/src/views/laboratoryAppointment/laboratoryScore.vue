<template>

  <div class="app-container">

    <el-row>

      <el-card class="qu-content" style="margin-top: 20px" v-for="item in list" :key="item.id">
        <p> 【简答题】 {{item.title}}(10分)</p>

        <div style="margin-top: 2px">
          答：{{item.content}}
        </div>

        <el-divider>打分</el-divider>
        <div>
          <el-input-number v-model="item.score" :min="0" :max="10" ></el-input-number>
        </div>
      </el-card>
      <el-card style="margin-top: 20px">
        <p>评价</p>
        <tinymce v-if="true" v-model="evaluation" :height="300"></tinymce>
      </el-card>


      <div style="margin-top: 20px">
        <el-button type="primary" icon="el-icon-right" @click="doHandler">
          提交评估
        </el-button>
      </div>
    </el-row>
  </div>

</template>

<script>
import { sendScore, getLaboratoryInfoByAppointmentId } from '@/api/laboratoryInfo'
import { Loading } from 'element-ui'
import { getToken } from '@/utils/auth'
import Tinymce from '@/components/Tinymce/index.vue'
export default {
  name: 'laboratoryScore',
  components: { Tinymce },

  data() {
    return {
      // 所有试题
      list:[],
      appointmentId: '',
      evaluation: ''
    }
  },
  created() {

    this.appointmentId = this.$route.params.id
    this.init()
  },

  methods: {
    init(){
      getLaboratoryInfoByAppointmentId(this.appointmentId).then(res=>{
        this.list = res.data.list
      })
    },


    // 提交试卷
    doHandler() {
      // 打开
      const loading = Loading.service({
        text: '提交中',
        background: 'rgba(0, 0, 0, 0.7)'
      })

      sendScore({
        list: this.list,
        appointmentId: this.appointmentId,
        teacherId: getToken(),
        evaluation: this.evaluation
      }).then(res=>{
        loading.close()
        this.$message({
          message: '实验评估成功',
          type: 'success'
        })
        this.$router.push({ name: 'laboratoryAssess' })
      })
    },

  }
}
</script>
