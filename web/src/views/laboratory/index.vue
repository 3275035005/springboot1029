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
      <el-table-column prop="title" label="实验标题" />
      <el-table-column prop="videoPath" label="观看视频">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-search" @click="videoDetails(scope.row.videoPath)">查看视频</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="实验科目">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === '1'">化学</el-tag>
          <el-tag v-if="scope.row.type === '2'" type="success">生物</el-tag>
          <el-tag v-if="scope.row.type === '3'" type="warning">物理</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="type1" label="单选题数量" />
      <el-table-column prop="type2" label="简答题数量" />
      <el-table-column label="预约统计" >
        <el-table-column prop="appointmentNumber" label="已预约" />
        <el-table-column prop="yesNumber" label="已实验" />
        <el-table-column prop="noNumber" label="未实验" />
        <el-table-column prop="closeNumber" label="取消预约" />
      </el-table-column>
      <el-table-column prop="status" label="实验状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '1'">正常</el-tag>
          <el-tag v-if="scope.row.status === '0'" type="warning">关闭</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form :model="formData" label-position="left" label-width="100px">
        <el-card>
        <el-form-item label="实验标题">
          <el-input v-model="formData.title" placeholder="请填写实验标题"/>
        </el-form-item>
          <el-form-item label="实验科目">
            <el-select v-model="formData.type" clearable placeholder="请选择实验科目">
              <el-option value="1" label="化学"/>
              <el-option value="2" label="生物"/>
              <el-option value="3" label="物理"/>
            </el-select>
          </el-form-item>
          <el-form-item label="实验状态">
            <el-select v-model="formData.status" clearable placeholder="请选择实验状态">
              <el-option value="1" label="正常"/>
              <el-option value="0" label="关闭"/>
            </el-select>
          </el-form-item>
          <el-form-item label="上传视频" >
            <el-upload
              ref="upload"
              action="http://localhost:9002/files/file"
              :on-success="fileHandleFilmUrlSuccess"
              accept=".mp4"
              multiple
              :limit="1">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>

        </el-card>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="实验视频"
      :visible.sync="dialogVisible2"
      width="50%"
      @close="handleClose"
    >
      <div id="video-player">
        <video id="video" controls autoplay>
          <source :src="videoPath" type="video/mp4">
        </video>
      </div>
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
import { deleteById, update, insert, pageQuery } from '@/api/laboratory'
import Tinymce from '@/components/Tinymce'
export default {
  components: { Tinymce }, // 注册组件
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      dialogVisible2: false,
      fileList: [],
      videoPath: '',
      formData: {
        list: []
      },
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
    fileHandleFilmUrlSuccess(response) {
      this.formData.videoPath = response.data.row;
      this.$refs.upload.clearFiles();
    },
    // 修改和更新功能
    updateAndSave(row) {
      if (row == null) {
        this.title = '新增实验信息'
        this.formData = {
          list: []
        }
      } else {
        this.title = '修改实验信息'
        this.formData = row
      }
      this.dialogVisible = true
    },
    videoDetails(videoPath) {
      this.videoPath = videoPath
      this.dialogVisible2 = true
    },
    handleClose(){
      this.$router.go(0)
    },
    // 确认按钮
    handleBtn() {
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


    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        deleteById(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              })
              // 刷新表格
              this.getList()
            }
          })
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
<style  rel="stylesheet/scss" lang="scss" scoped>
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

</style>
