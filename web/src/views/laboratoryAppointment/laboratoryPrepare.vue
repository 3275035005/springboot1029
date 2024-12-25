<template>
  <div class="app-container">

    <el-row :gutter="24">

      <el-col :span="24" style="margin-bottom: 20px">

        <el-alert
          title="点击`开始实验`后将自动进入实验测试，请诚信实验测试！"
          type="error"
          style="margin-bottom: 10px"
        />

        <el-card class="pre-exam">

          <div><strong>实验标题：</strong>{{ detailData.title }}</div>
          <div><strong>试卷总分：</strong>100分</div>
          <div><strong>及格分数：</strong>60分</div>
          <div><strong>实验科目：</strong>{{ types[parseInt(detailData.type)]}}</div>
        </el-card>

      </el-col>

      <el-col :span="24">

        <el-button type="primary" icon="el-icon-caret-right" @click="handleCreate">
          开始实验
        </el-button>

        <el-button @click="handleBack">
          返回
        </el-button>

      </el-col>

    </el-row>
  </div>
</template>

<script>
import { getLaboratoryById } from '@/api/laboratory'
export default {
  data() {
    return {
      detailData: {},
      types: ['', '化学', '生物', '物理'],
      laboratoryId: '',
      appointmentId:''
    }
  },

  created() {
    this.laboratoryId = this.$route.params.id
    this.appointmentId = this.$route.params.appointmentId
    this.fetchData()
  },

  methods: {

    fetchData() {
      getLaboratoryById(this.laboratoryId).then(response => {
        this.detailData = response.data.row
      })
    },

    handleCreate() {
      console.log(this.detailData)
       this.$router.push({ name: 'laboratoryStart', params: { id: this.laboratoryId, appointmentId: this.appointmentId}})
    },
    handleBack() {
      this.$router.push({ name: 'myProcedure' })
    }

  }
}
</script>

<style scoped>

  .pre-exam div {

    line-height: 42px;
    color: #555555;
  }

</style>

