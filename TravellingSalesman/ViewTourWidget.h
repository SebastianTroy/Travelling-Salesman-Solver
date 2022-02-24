#ifndef VIEWTOURWIDGET_H
#define VIEWTOURWIDGET_H

#include "Tour.h"

#include <QWidget>

#include <memory>

class ViewTourWidget : public QWidget {
    Q_OBJECT
public:
    explicit ViewTourWidget(QWidget* parent = nullptr);
    virtual ~ViewTourWidget() {}

    void SetHighContrastMode(bool highContrast);
    void SetTour(std::shared_ptr<Tour> tour);

protected:
    virtual void mousePressEvent(QMouseEvent* event) override;
    virtual void mouseReleaseEvent(QMouseEvent* event) override;
    virtual void mouseMoveEvent(QMouseEvent* event) override;

    virtual void showEvent(QShowEvent* event) override;
    virtual void resizeEvent(QResizeEvent* event) override;

    virtual void paintEvent(QPaintEvent* event) override;

private:
    static const inline int stopRadius_ = 15;

    bool mouseDown_;
    QPointF mousePreviousPosition_;

    bool highContrastMode_;

    std::shared_ptr<Tour> tour_;
};

#endif // VIEWTOURWIDGET_H
