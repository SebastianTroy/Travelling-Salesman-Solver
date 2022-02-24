#include "ViewTourWidget.h"

#include <QMouseEvent>
#include <QPainter>
#include <QPainterPath>
#include <QGradient>

ViewTourWidget::ViewTourWidget(QWidget* parent)
    : QWidget(parent)
    , highContrastMode_(false)
{
}

void ViewTourWidget::SetHighContrastMode(bool highContrast)
{
    highContrastMode_ = highContrast;
    update();
}

void ViewTourWidget::SetTour(std::shared_ptr<Tour> tour)
{
    tour_ = tour;
    if (tour_) {
        connect(tour_.get(), &Tour::onTourUpdated, this, static_cast<void(ViewTourWidget::*)()>(&ViewTourWidget::update));
    }
}

void ViewTourWidget::mousePressEvent(QMouseEvent* event)
{
    mouseDown_ = true;
    mousePreviousPosition_ = event->position();
    if (tour_) {
        if (event->button() == Qt::MouseButton::LeftButton) {
            bool collides = false;
            tour_->ForEachStop([&](const QPointF stop)
            {
                double distance = std::sqrt(std::pow(stop.x() - event->position().x(), 2.0) + std::pow(stop.y() - event->position().y(), 2.0));
                if (distance < stopRadius_) {
                    collides = true;
                }
            });
            if (!collides) {
                tour_->AddStop(event->position());
                update();
            }
        } else if (event->button() == Qt::MouseButton::RightButton) {
            tour_->RemoveStopsUnder(event->position(), stopRadius_);
            update();
        }
    }
}

void ViewTourWidget::mouseReleaseEvent(QMouseEvent* event)
{
    mouseDown_ = false;
    mousePreviousPosition_ = event->position();
    if (tour_) {
        tour_->CullStopsOutOfBounds();
        update();
    }
}

void ViewTourWidget::mouseMoveEvent(QMouseEvent* event)
{
    if (mouseDown_) {
        if (tour_) {
            if (event->buttons() & Qt::MouseButton::LeftButton) {
                tour_->MoveStopUnder(QLineF(mousePreviousPosition_, event->position()), stopRadius_);
                update();
            } else if (event->buttons() & Qt::MouseButton::RightButton) {
                tour_->RemoveStopsUnder(event->position(), stopRadius_);
                update();
            }
        }
        mousePreviousPosition_ = event->position();
    }
}

void ViewTourWidget::showEvent(QShowEvent* /*event*/)
{
    if (tour_) {
        tour_->ScaleTo(rect());
    }
}

void ViewTourWidget::resizeEvent(QResizeEvent* /*event*/)
{
    if (tour_) {
        tour_->ScaleTo(rect());
    }
}

void ViewTourWidget::paintEvent(QPaintEvent* /*event*/)
{
    if (tour_) {
        QPainter p(this);
        p.setRenderHint(QPainter::Antialiasing, true);


        // Draw route
        QPainterPath route;
        bool firstPoint = true;
        tour_->ForEachStop([&](const QPointF& stop)
        {
            if (firstPoint) {
                route.moveTo(stop);
                route.lineTo(stop);
                firstPoint = false;
            } else {
                route.lineTo(stop);
            }
        });
        if (tour_->IsLooped()) {
            route.closeSubpath();
        }

        if (highContrastMode_) {
            p.fillRect(rect(), Qt::black);
            p.setPen(Qt::white);
            p.drawPath(route);
        } else {
            p.fillRect(rect(), QBrush(QGradient(QGradient::Preset::YoungGrass)));

            QPen road(Qt::darkGray);
            road.setWidthF(9.5);
            p.setPen(road);
            p.drawPath(route);

            QPen roadMarkings(Qt::white);
            roadMarkings.setStyle(Qt::DashLine);
            roadMarkings.setWidthF(1.75);
            p.setPen(roadMarkings);
            p.drawPath(route);

            // Draw stops over route
            tour_->ForEachStop([&p, city = QPixmap(":/City.png")](const QPointF& stop)
            {
                p.drawPixmap(stop.x() - stopRadius_, stop.y() - stopRadius_, stopRadius_ * 2, stopRadius_ * 2, city);
            });
        }
    }
}
