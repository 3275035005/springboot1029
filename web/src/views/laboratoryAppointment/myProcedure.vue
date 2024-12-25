<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="实验标题">
        <el-input v-model="dataVo.title" placeholder="请输入实验标题"/>
      </el-form-item>
      <el-form-item label="实验科目">
        <el-select v-model="dataVo.type" clearable placeholder="请选择实验科目">
          <el-option value="1" label="化学" />
          <el-option value="2" label="生物" />
          <el-option value="3" label="物理" />
        </el-select>
      </el-form-item>
      <el-form-item label="实验状态">
        <el-select v-model="dataVo.type" clearable placeholder="请选择实验状态">
          <el-option value="0" label="未开始" />
          <el-option value="1" label="等待参加" />
          <el-option value="2" label="实验结束" />
          <el-option value="3" label="未实验" />
          <el-option value="4" label="取消预约" />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >
      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column prop="title" label="实验标题" />
      <el-table-column prop="type" label="实验科目">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === '1'">化学</el-tag>
          <el-tag v-if="scope.row.type === '2'" type="success">生物</el-tag>
          <el-tag v-if="scope.row.type === '3'" type="warning">物理</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="appointmentTime" label="实验预约时间" />
      <el-table-column prop="laboratoryTime" label="实验提交时间" />
      <el-table-column prop="status" label="实验状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" >未开始</el-tag>
          <el-tag v-if="scope.row.status === '1'" >等待参加</el-tag>
          <el-tag v-if="scope.row.status === '2'" type="warning">实验结束</el-tag>
          <el-tag v-if="scope.row.status === '3'" type="warning">未实验</el-tag>
          <el-tag v-if="scope.row.status === '4'" type="warning">取消预约</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="操作时间" />
      <el-table-column label="操作" align="center" width="400">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === '2'" icon="el-icon-thumb" size="mini" type="success" @click="handlePg(scope.row)">查看实验评估</el-button>
          <el-button v-if="scope.row.status === '0'" icon="el-icon-s-fold" size="mini" disabled>待开始</el-button>
          <el-button v-if="scope.row.status=== '1'" icon="el-icon-caret-right" type="primary" size="mini" @click="handlePre(scope.row)">去实验</el-button>
          <el-button v-if="scope.row.status === '2'" icon="el-icon-s-unfold" size="mini" disabled>已结束</el-button>
          <el-button v-if="scope.row.status === '0'" icon="el-icon-thumb" size="mini" type="warning" @click="handleCancel(scope.row.id)">取消预约</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 实验评估 -->
    <el-dialog
      title="实验评估结果"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form :model="infoDate" label-position="left" label-width="100px">
        <el-form-item label="评估分数">
          <el-input-number v-model="infoDate.score" :disabled="true"></el-input-number>
        </el-form-item>
        <el-form-item label="评价">
          <tinymce  v-if="dialogVisible" v-model="infoDate.evaluation" :height="300" :disabled="true"></tinymce>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleClose">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList"
    />
  </div>
</template>
<script>
import { pageQuery, cancelAppointment } from '@/api/laboratoryAppointment'
import { getToken } from '@/utils/auth'
import Tinymce from '@/components/Tinymce/index.vue'
export default {
  components: { Tinymce },
  data() { // 定义变量和初始值
    return {
      activeName: 'first',
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      content: '',
      fileList: [],
      formData: {},
      title: '',
      infoDate: {}
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: { // 创建具体的方法, 定义的方法
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      this.dataVo.userId = getToken();
      pageQuery(this.page, this.limit, this.dataVo)

        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },
    // 开始实验
    handlePre(row) {
      this.$router.push({ name: 'laboratoryPrepare', params: { id: row.laboratoryId,  appointmentId: row.id}})
    },
    // 查看实验评估
    handlePg(row) {
      if (row.score === null) {
        this.$message({
          type: 'error',
          message: '请等待教师评估！'
        })
      } else {
        this.infoDate = row
        this.dialogVisible = true
      }
    },
    handleCancel(id){
      cancelAppointment(id).then(res=>{
        this.$message({
          type: 'success',
          message: '取消预约成功！'
        })
        this.getList()
      })

    },
    handleClose() {
      this.dialogVisible = false
      this.getList()
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {}
      this.page = 1
      this.limit = 10
      this.getList()
    }
  }
}
</script>
