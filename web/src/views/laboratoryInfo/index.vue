<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="实验资源标题">
        <el-select v-model="formData.questionId" placeholder="请选择实验资源标题">
          <el-option
            v-for="question in questionList"
            :key="question.id"
            :label="question.title"
            :value="question.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试题内容">
        <el-input v-model="formData.title" type="number" placeholder="请填写试题内容"/>
      </el-form-item>
      <el-form-item label="试题类型">
        <el-select v-model="dataVo.type" clearable placeholder="请选择试题类型">
          <el-option value="1" label="单选题"/>
          <el-option value="2" label="简答题"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
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
      <el-table-column prop="status" label="试题类型">
        <template slot-scope="scope">
          <span v-if="scope.row.type === '1'">单选题</span>
          <span v-if="scope.row.type === '2'">简答题</span>
        </template>
      </el-table-column>
      <el-table-column prop="laboratoryName" label="归属实验资源"/>
      <el-table-column prop="title" label="试题内容"/>
      <el-table-column prop="createTime" label="试题创建时间"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="60%"
    >

      <el-form :model="formData" label-position="left" label-width="120px">
        <el-card>
          <el-form-item label="题库名称">
            <el-select v-model="formData.laboratoryId" placeholder="请选择归属题库名称">
              <el-option
                v-for="question in questionList"
                :key="question.id"
                :label="question.title"
                :value="question.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="试题内容">
            <el-input type="textarea"
                      :rows="5" v-model="formData.title" placeholder="请输入试题内容"/>
          </el-form-item>
          <el-form-item label="试题类型">
            <el-select v-model="formData.type" clearable placeholder="请选择试题类型">
              <el-option value="1" label="单选题"/>
              <el-option value="2" label="简答题"/>
            </el-select>
          </el-form-item>
          <el-form-item label="单选题类型" v-if="formData.type == '1'">
            <el-select v-model="formData.tag" clearable placeholder="请选择单选题类型"  @change="handleTypeChange">
              <el-option value="1" label="仪器"/>
              <el-option value="2" label="试剂"/>
            </el-select>
          </el-form-item>
        </el-card>
        <div v-if="formData.tag=='1'" class="filter-container" style="margin-top: 25px">
          <el-button style="margin-bottom: 30px" class="filter-item" type="primary" icon="el-icon-plus" size="small" plain @click="handleAdd">
            添加
          </el-button>

          <el-table
            :data="formData.list"
            :border="true"
            style="width: 100%;"
            @row-click="toDetail"
            :row-class-name="tableRowClassName"
          >
            <el-table-column
              label="是否答案"

              align="center"
            >
              <template slot-scope="scope">

                <el-checkbox v-model="scope.row.flag">答案</el-checkbox>

              </template>

            </el-table-column>
            <el-table-column
              label="选项图片"
              align="center"
            >
              <template v-slot="scope">
                <el-upload
                  ref="upload"
                  :limit="1"
                  action="http://localhost:9002/files/file"
                  :on-success="fileHandleSuccess"
                  accept="image/*"
                  class="avatar-uploader"
                >
                  <img v-if="scope.row.content" :src="scope.row.content" class="avatar">
                  <el-button v-else size="small" type="primary">点击上传</el-button>
                </el-upload>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"

            >
              <template slot-scope="scope">
                <el-button type="danger" icon="el-icon-delete" circle @click="removeItem(scope.$index)" />
              </template>
            </el-table-column>

          </el-table>

        </div>
        <div v-if="formData.tag=='2'" class="filter-container" style="margin-top: 25px">
          <el-button style="margin-bottom: 30px" class="filter-item" type="primary" icon="el-icon-plus" size="small" plain @click="handleAdd">
            添加
          </el-button>

          <el-table
            :data="formData.list"
            :border="true"
            style="width: 100%;"
          >
            <el-table-column
              label="是否答案"
              width="120"
              align="center"
            >
              <template slot-scope="scope">

                <el-checkbox v-model="scope.row.flag">答案</el-checkbox>

              </template>

            </el-table-column>
            <el-table-column
              label="答案内容"
            >
              <template slot-scope="scope">
                <el-input v-model="scope.row.content" type="textarea"/>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              width="100px"
            >
              <template slot-scope="scope">
                <el-button type="danger" icon="el-icon-delete" circle @click="removeItem(scope.$index)" />
              </template>
            </el-table-column>

          </el-table>

        </div>

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
import { getInfoId, insert, update, pageQuery } from '@/api/laboratoryInfo'
import { getLaboratoryAll } from '@/api/laboratory'

export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 10, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      formData: {
        list:[]
      },
      row_index: 0,
      fileList: [],
      title: '',
      questionList: []
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
    this.getQuestionList()
  },
  methods: { // 创建具体的方法, 定义的方法
    getQuestionList() {
      getLaboratoryAll().then(res => {
        this.questionList = res.data.row
      })
    },
    tableRowClassName({row, rowIndex}) {
      row.row_index = rowIndex;
    },
    toDetail(row, column, event){
      this.row_index = row.row_index
    },
    // 上传头像成功
    fileHandleSuccess(response) {
      this.formData.list[this.row_index].content = response.data.row;
      this.$refs.upload.clearFiles(); //去掉文件列表
    },
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
      if (row == null) {
        this.title = '新增试题信息'
        this.formData = {
          list:[]
        }
      } else {
        this.title = '修改试题信息'
        this.formData = row;
        this.getQuestionInfo(row.infoId);
      }
      this.dialogVisible = true
    },
    getQuestionInfo(id){
      getInfoId(id).then(res => {
        this.formData.list = res.data.row
      })
    },

    // 确认按钮
    handleBtn() {
      if (this.formData.type === '1') {
        let rightCount = 0
        this.formData.list.forEach(function(item) {
          if (item.flag) {
            rightCount += 1
          }
        })

        if (rightCount !== 1) {
          this.$message({
            message: '单选题答案只能有一个',
            type: 'warning'
          })

          return
        }
      }

      if (this.formData.type ===  '2') {
        this.formData.list = []
      }

      // 关闭弹窗
      this.dialogVisible = false
      if (this.formData.id) {
        update(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            this.getList()
          })
      } else {
        insert(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '新增成功！'
            })
            this.getList()
          })
      }
    },

    // 删除子项
    removeItem(index){
      this.formData.list.splice(index, 1)
    },

    // 添加子项
    handleAdd() {
      this.formData.list.push({
        content: '',
        flag: ''
      })
    },

    handleTypeChange() {
      this.formData.list = []
      this.formData.list.push({ flag: false, content: '', title: '' })
      this.formData.list.push({ flag: false, content: '', title: '' })
      this.formData.list.push({ flag: false, content: '', title: '' })
      this.formData.list.push({ flag: false, content: '', title: '' })
    },

    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {}
      this.page = 1 // 当前页
      this.limit = 10 // 每页记录数
      this.getList()
    }
  }
}
</script>
