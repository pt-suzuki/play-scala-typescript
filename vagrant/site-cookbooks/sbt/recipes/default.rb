execute "sbt_download" do
  user "root"
  command "curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo"
  action :run
end

package "sbt" do
  action :install
end