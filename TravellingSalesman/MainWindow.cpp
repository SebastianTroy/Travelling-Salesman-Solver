#include "MainWindow.h"
#include "./ui_MainWindow.h"

#include <QDesktopServices>
#include <QLocale>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui_(new Ui::MainWindow)
    , loopTimer_(this)
    , iterations_(0)
    , improvements_(0)
    , lastImprovement_(0)
{
    ui_->setupUi(this);

    setWindowIcon(QIcon(":/Logo.png"));
    setWindowTitle("Travelling Salesman Solver v3.0");

    tour_ = std::make_shared<Tour>(ui_->viewTourWidget->rect());
    ui_->viewTourWidget->SetTour(tour_);

    ///
    /// Tour Options
    ///
    connect(ui_->tourLoopTourCheckbox, &QCheckBox::clicked, tour_.get(), &Tour::SetLooped);
    connect(ui_->tourAddStopsButton, &QPushButton::pressed, this, [this, tour = tour_]()
    {
        if (tour) {
            tour->AddRandomStops(ui_->tourAddStopsCountSpinBox->value());
            ResetInfo();
        }
    });
    connect(ui_->tourRandomiseButton, &QPushButton::pressed, tour_.get(), &Tour::Shuffle);
    connect(ui_->tourRandomiseButton, &QPushButton::pressed, this, &MainWindow::ResetInfo);
    connect(ui_->tourRemoveAllStopsButton, &QPushButton::pressed, tour_.get(), &Tour::Clear);
    connect(ui_->tourRemoveAllStopsButton, &QPushButton::pressed, this, &MainWindow::ResetInfo);
    ui_->tourLoopTourCheckbox->setChecked(false);

    ///
    /// Algorithm Options
    ///
    connect(ui_->algorithmMethodSelectComboBox, &QComboBox::currentIndexChanged, this, [this](int index)
    {
        auto& [ name, description, action ] = mutations_[index];{}
        Q_UNUSED(name);
        Q_UNUSED(action);
        ui_->algorithmCurrentMethodDetailsTextBrowser->setText(description);
    });
    CreateMutations();
    for (const auto& [ name, description, action ] : mutations_) {
        Q_UNUSED(description);
        Q_UNUSED(action);
        ui_->algorithmMethodSelectComboBox->addItem(name);
    }
    ui_->algorithmCycleMethodsCheckbox->setChecked(true);

    ///
    /// Graphics
    ///
    connect(ui_->graphicsHighContrastComboBox, &QCheckBox::clicked, ui_->viewTourWidget, &ViewTourWidget::SetHighContrastMode);

    ///
    /// Websites
    ///

    // Link to website
    connect(ui_->moreByTroyDevButton, &QPushButton::pressed, this, []()
    {
        QDesktopServices::openUrl(QUrl("https://www.TroyDev.co.uk"));
    });
    // Attribute artwork
    connect(ui_->iconAttributionButton, &QPushButton::pressed, this, []()
    {
        QDesktopServices::openUrl(QUrl("https://www.flaticon.com/authors/eucalyp"));
    });

    ///
    /// Loops
    ///
    loopTimer_.setSingleShot(false);
    loopTimer_.setInterval(0);
    connect(&loopTimer_, &QTimer::timeout, this, &MainWindow::MutateTour);
    loopTimer_.start();

    oneSecondPingTimer_.setSingleShot(false);
    oneSecondPingTimer_.setInterval(2000);
    connect(&oneSecondPingTimer_, &QTimer::timeout, this, &MainWindow::SelectNextMutationType);
    oneSecondPingTimer_.start();
}

MainWindow::~MainWindow()
{
    delete ui_;
}

void MainWindow::CreateMutations()
{
    mutations_.push_back(std::make_tuple("Randomise Stop", "The position of one stop in the tour is randomised", [&]() -> void
    {
        if (tour_) {
            tour_->RandomiseStopLocation();
        }
    }));
    mutations_.push_back(std::make_tuple("Randomise Stops", "The position of a number of stops in the tour is randomised", [&]() -> void
    {
        if (tour_) {
            tour_->RandomiseStopsLocations();
        }
    }));
    mutations_.push_back(std::make_tuple("Reverse Section", "Selects a random subsection of the tour and reverses the order of the stops", [&]() -> void
    {
        if (tour_) {
            tour_->ReverseSection();
        }
    }));
    mutations_.push_back(std::make_tuple("Move Section", "Move a section of the tour to the start or the end", [&]() -> void
    {
        if (tour_) {
            tour_->MoveSection();
        }
    }));
    mutations_.push_back(std::make_tuple("Swap Stops", "Swaps the position of two stops in the tour", [&]() -> void
    {
        if (tour_) {
            tour_->SwapStops();
        }
    }));
}

void MainWindow::MutateTour()
{
    if (tour_) {
        int currentIndex = ui_->algorithmMethodSelectComboBox->currentIndex();
        auto& [ name, description, mutation ] = mutations_[currentIndex];{}
        Q_UNUSED(name);
        Q_UNUSED(description);
        qulonglong repeats = 1000;
        for (qulonglong i = 0; i < repeats; ++i) {
            double oldDistance = tour_->GetDistance();
            auto copy = tour_->GetTour();
            std::invoke(mutation);
            double newDistance = tour_->GetDistance();
            if (newDistance > oldDistance) {
                tour_->SetTour(std::move(copy));
            } else if (newDistance < oldDistance) {
                ui_->viewTourWidget->update();
                ++improvements_;
                lastImprovement_ = iterations_ + i + 1;
            }
        }
        iterations_ += repeats;

        ui_->infoIterationsValueLabel->setText(QLocale::system().toString(iterations_));
        ui_->infoImprovementsValueLabel->setText(QLocale::system().toString(improvements_));
        ui_->infoLastImprovementValueLabel->setText(QLocale::system().toString(lastImprovement_));
    }
}

void MainWindow::ResetInfo()
{
    improvements_ = 0;
    iterations_ = 0;
    lastImprovement_ = 0;
    ui_->infoIterationsValueLabel->setText(QLocale::system().toString(iterations_));
    ui_->infoImprovementsValueLabel->setText(QLocale::system().toString(improvements_));
    ui_->infoLastImprovementValueLabel->setText(QLocale::system().toString(lastImprovement_));
}

void MainWindow::SelectNextMutationType()
{
    if (ui_->algorithmCycleMethodsCheckbox->isChecked()) {
        int currentIndex = ui_->algorithmMethodSelectComboBox->currentIndex();
        int nextIndex = (currentIndex + 1) % ui_->algorithmMethodSelectComboBox->count();
        ui_->algorithmMethodSelectComboBox->setCurrentIndex(nextIndex);
    }
}
