#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "Tour.h"

#include <QMainWindow>
#include <QTimer>

#include <vector>
#include <tuple>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow {
    Q_OBJECT
public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private:
    Ui::MainWindow *ui_;

    QTimer loopTimer_;
    QTimer oneSecondPingTimer_;
    qulonglong iterations_;
    qulonglong improvements_;
    qulonglong lastImprovement_;
    std::shared_ptr<Tour> tour_;
    std::vector<std::tuple<QString, QString, std::function<void()>>> mutations_;

    void CreateMutations();
    void MutateTour();
    void ResetInfo();
    void SelectNextMutationType();
};
#endif // MAINWINDOW_H
