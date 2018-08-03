# epel-repository
yum_package "epel-release" do
    action :install
end

# remi-repository
execute "yum-repo" do
  user 'root'
  command 'yum -y install http://rpms.famillecollet.com/enterprise/remi-release-7.rpm'
  creates "/etc/yum.repos.d/remi.repo"
end
