#!/usr/bin/env bash
#!/usr/bin/env bash
:||{
....@Author: zhz
....@Time: 2018年7月31日14:34:14
....多选注释: 参数列表
    $1: 项目名称, 如ncis,yc,manage,central等
    $2: 操作类型: compile start
}

#startup.sh所有系统目录
base_path="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# 第一步 ： svn 更新
cd $base_path
git pull

# 第二步：编辑
case $1 in
	'push')
	cd $base_path
	git add .
	git commit -m $2
	git push
   ;;
 *)
    echo 编辑所有
    cd $base_path/cg-parent
    mvn clean install -Dmaven.test.skip=true
    cd $base_path
    mvn clean install -Dmaven.test.skip=true
;;
esac



