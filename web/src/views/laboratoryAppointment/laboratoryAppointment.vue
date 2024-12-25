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
      <el-table-column prop="type1" label="单选题数量" />
      <el-table-column prop="type2" label="简答题数量" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status=== '1'" icon="el-icon-caret-right" type="primary" size="mini" @click="updateAndSave(scope.row)">预约实验</el-button>
          <el-button v-if="scope.row.status === '0'" icon="el-icon-s-unfold" size="mini" disabled>已关闭</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="预约时间">
          <el-date-picker v-model="formData.appointmentTime"  type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择预约时间"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
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
import { pageQuery } from '@/api/laboratory'
import { appointment } from '@/api/laboratoryAppointment'
import { getToken } from '@/utils/auth'
export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      fileList: [],
      formData: {},
      title: ''
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: { // 创建具体的方法, 定义的方法
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },
    // 修改和更新功能
    updateAndSave(row) {
      this.title = '实验预约'
      this.formData = {
        laboratoryId: row.id,
        appointmentTime: '',
        userId: getToken()
      }
      this.dialogVisible = true
    },
    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      appointment(this.formData)
        .then(response => { // 请求成功
          this.$message({
            type: 'success',
            message: '预约成功！'
          })
          this.getList()
        })
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {};
      this.page = 1; // 当前页
      this.limit = 10; // 当前页
      this.getList()
    }
  }
}
</script>
